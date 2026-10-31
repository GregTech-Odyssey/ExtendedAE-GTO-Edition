package com.glodblock.github.extendedae.common.me.taglist;

import net.minecraft.Util;
import net.minecraftforge.registries.ForgeRegistries;

import appeng.api.stacks.AEKey;
import appeng.hooks.IUnique;
import appeng.util.prioritylist.IPartitionList;

import com.gto.fastcollection.cache.WeakValueHashCache;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

public class TagPriorityList implements IPartitionList {

    private static final TagPriorityList EMPTY = new TagPriorityList("", "");
    private static final WeakValueHashCache<Expressions, TagPriorityList> CACHE = new WeakValueHashCache<>();
    private static final Function<Expressions, TagPriorityList> FACTORY = expressions -> new TagPriorityList(expressions.whiteList(), expressions.blackList());
    private final String rawWhiteListExpression;
    private final String rawBlackListExpression;

    // Compiled predicates for efficient evaluation.
    private final Predicate<Set<String>> whiteListPredicate;
    private final Predicate<Set<String>> blackListPredicate;

    private final boolean isWhitelistActive;

    private final boolean empty;
    private volatile IntOpenHashSet matchingIds;

    /**
     * Creates a tag-based partition list using complex filter expressions.
     *
     * @param whiteListExpression The expression for the whitelist (e.g., "forge:ingots & !forge:ingots/iron").
     *                            If empty or null, the whitelist is inactive.
     * @param blackListExpression The expression for the blacklist (e.g., "minecraft:logs | minecraft:planks").
     */
    public TagPriorityList(String whiteListExpression, String blackListExpression) {
        this.rawWhiteListExpression = this.removeBlank(whiteListExpression != null ? whiteListExpression : "");
        this.rawBlackListExpression = this.removeBlank(blackListExpression != null ? blackListExpression : "");

        // Compile the expressions using the new parser.
        this.whiteListPredicate = TagExpParser.compile(this.rawWhiteListExpression);
        this.blackListPredicate = TagExpParser.compile(this.rawBlackListExpression);

        // Determine if the whitelist should be actively checked.
        // An empty/whitespace-only expression means the whitelist doesn't restrict anything.
        this.isWhitelistActive = !this.rawWhiteListExpression.isBlank();

        this.empty = this.rawWhiteListExpression.isBlank() && this.rawBlackListExpression.isBlank();
        if (!this.empty) {
            CompletableFuture.runAsync(() -> {
                var ids = new IntOpenHashSet();
                ForgeRegistries.ITEMS.forEach(item -> {
                    if (eval(item)) ids.add(((IUnique) item).ae2$getUid());
                });
                ForgeRegistries.FLUIDS.forEach(fluid -> {
                    if (eval(fluid)) ids.add(((IUnique) fluid).ae2$getUid());
                });
                this.matchingIds = ids;
            }, Util.backgroundExecutor());
        }
    }

    private String removeBlank(String raw) {
        return raw.replaceAll("\\s+", "");
    }

    public static TagPriorityList getCached(String whiteList, String blackList) {
        if (whiteList.isBlank() && blackList.isBlank()) return EMPTY;
        return CACHE.getCache(new Expressions(whiteList, blackList), FACTORY);
    }

    public boolean isReady() {
        return this.empty || this.matchingIds != null;
    }

    private record Expressions(String whiteList, String blackList) {}

    @Override
    public boolean isListed(AEKey input) {
        if (this.empty) return true;
        var ids = this.matchingIds;
        return ids != null && ids.contains(input.getUid());
    }

    @Override
    public boolean isEmpty() {
        // The filter is considered empty if neither a whitelist nor a blacklist expression is provided.
        return this.empty;
    }

    @Override
    public Iterable<AEKey> getItems() {
        // This partition list dynamically evaluates tags, it doesn't hold a predefined list of items.
        return Collections.emptyList();
    }

    /**
     * Evaluates if the given Primary Key matches the filter rules (whitelist/blacklist).
     * Used once while building the shared item and fluid index.
     *
     * @param input The Primary Key (Item or Fluid key) to evaluate.
     * @return True if the key passes the filter rules, false otherwise.
     */
    private boolean eval(@NotNull Object input) {
        // Evaluate both predicates against the key's tags.
        // TagExpParser.evaluate handles getting the tags from the key.
        final boolean whiteMatches = TagExpParser.evaluate(this.whiteListPredicate, input);
        final boolean blackMatches = TagExpParser.evaluate(this.blackListPredicate, input);

        // Apply standard filter logic:
        // - If whitelist is active, must match whitelist AND NOT match blacklist.
        // - If whitelist is inactive, must NOT match blacklist.
        if (this.isWhitelistActive) {
            return whiteMatches && !blackMatches;
        } else {
            return !blackMatches;
        }
    }
}
