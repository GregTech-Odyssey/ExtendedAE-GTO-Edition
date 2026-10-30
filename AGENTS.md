# AGENTS

本仓库是 GregTech Odyssey 团队维护的 ExtendedAE GTO Edition，当前维护 `gto` 分支（Minecraft 1.20.1 Forge）。对外介绍见 [README.md](README.md)，玩家可感知的差异见 [GTO_CHANGES.md](GTO_CHANGES.md)，开发与发布流程见 [docs/development.md](docs/development.md)。

## 修改约定

- 任何改变玩家可感知行为的修改，都要在实现过程中同步维护 `GTO_CHANGES.md`，不等提交、发布或用户提醒时补写。整合包接入发生变化时，同时维护 GTOCore 的当前周更新记录。
- 保持模组 ID、资源命名空间 `expatternprovider` 和 Java 包名 `com.glodblock.github.extendedae`；显示名使用 `ExtendedAE GTO Edition`。保留原作者、素材与翻译贡献者的署名及许可证。
- 问题反馈统一指向 [GregTech Odyssey Issue tracker](https://github.com/GregTech-Odyssey/GregTech-Odyssey/issues)。修改 README、模组元数据、Maven POM 或 GitHub Issue 入口时保持一致。
- README 只介绍项目、使用环境、差异、署名、许可与反馈入口；构建、发布及代理协作细节放在开发文档。
- 首次编辑或审查 Java/Kotlin 前，在相邻的 GTOCore 工作区读取 `AGENTS.md` 和 `CODING_GUIDELINES.md` 中与改动相关的章节，完成后按实际 diff 复查。GTOCore 的目录名可能是 `GTOCore-Main` 或 `texture-overhaul2`，不要依赖固定绝对路径。
- 只以 GTO fork 的实际源码或依赖字节码为准：AE2 默认查找相邻 `Applied-Energistics-2-gto`，EMI 查找 `EMI-gto`；版本以本仓 `gradle.properties` 为准。不要用上游源码或记忆替代签名核对；缺少检出时先报告，不自行克隆或切换其他工作区。
- ExtendedAE 自身的通用兼容逻辑直接在本仓实现，不在 AE2 或 GTOCore 重复加 Mixin。GTOCore 的分级机器、传送与专属界面等整合包规则仍留在 GTOCore；不要新增对 GTOCore、GTOLib 的强依赖。
- 配方查看与填充维护在 `xmod/emi`，复用 AE2 GTO 的 EMI 接口，不恢复旧 JEI、REI 集成。
- `AEKey` 按引用语义处理，优先使用 fork 已有的 `KeyCounter`、`AEKeyMap` 等容器与模糊查询 API；热路径不写全表扫描、streams 或临时集合来替代已有索引。
- `src/generated/resources` 是 datagen 输出；修改生成内容时改真实生成源后执行 `runData`，不将手工修改生成文件作为最终结果。

## 构建与发布

- Windows 第一次调用 Gradle 前显式设置有效的 JDK 21，并确认 `JAVA_HOME/bin/java.exe` 存在。Java 源码使用 LF；提交、推送或执行 `build` / `assemble` 前先运行 `spotlessApply`、`spotlessCheck`。
- 验证范围与改动相称：纯文档不跑 Gradle，元数据用 `processResources`，Java 改动至少 `compileJava`，生成内容用 `runData`；本仓 `build` / `assemble` 不代替显式单测。
- 发布使用 `com.gto.gtopublishgradleplugin`；版本写在 `gradle.properties` 的 `mod_version`，必须为 `x.y.z[-suffix]`。插件自动附加 `forge-1.20.1`，不要把平台写进版本或重复写进基础制品名。
- Maven 坐标为 `com.glodblock.github.extendedae:extendedae-forge-1.20.1:<版本>`，只发布重混淆后的制品。当前只启用 Maven，GitHub Release 与 CurseForge 未启用。
- 凭据只放在 Gradle 用户目录的 `gradle.properties` 或相应本地配置中，不写入仓库、日志与文档。具体任务和依赖版本同步要求见开发文档。

## 协作与收尾

- 开始前检查 Git 状态，保留用户已有与无关改动，不用 reset、checkout 或 stash 清理他人的工作。
- 提交只带自己的路径（`git commit --only <paths>`）；推送前先 fetch 并核对远端差距。
- 收尾报告实际执行的验证及未完成项；不要用无关检查替代必要检查。
