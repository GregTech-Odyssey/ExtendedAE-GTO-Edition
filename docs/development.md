# ExtendedAE GTO Edition 开发与发布

本仓库只维护 Minecraft 1.20.1 Forge，构建插件为 ModDevGradle legacyforge，Gradle 与模组字节码均使用 JDK 21。常驻协作约定见 [AGENTS.md](../AGENTS.md)，玩家可感知的差异见 [GTO_CHANGES.md](../GTO_CHANGES.md)。

## 环境与常用任务

Windows PowerShell 首次调用 Gradle 前显式设置 JDK 21，先确认 Java 可执行文件存在：

```powershell
$env:JAVA_HOME = '<JDK_21_HOME>'
if (-not (Test-Path -LiteralPath "$env:JAVA_HOME\bin\java.exe")) { throw 'Valid JDK 21 required' }
.\gradlew.bat spotlessApply
.\gradlew.bat spotlessCheck
.\gradlew.bat compileJava
```

macOS / Linux 将 `JAVA_HOME` 指向 JDK 21，使用 `./gradlew` 执行相同任务。IDE 的 Gradle JVM 与项目 JDK 也选择 21。

| 任务 | 用途 |
|---|---|
| `spotlessApply` / `spotlessCheck` | 应用 / 检查 Java 格式、导入和 LF 行尾 |
| `compileJava` | 编译 Java；会先执行 Spotless 检查 |
| `processResources` | 展开模组元数据中的版本、名称与描述 |
| `runClient` / `runServer` | 启动开发客户端 / 服务端 |
| `runData` | 从生成源输出到 `src/generated/resources` |
| `assemble` | 生成 jar 并执行 `reobfJar` |
| `generatePomFileForMavenJavaPublication` | 生成 Maven POM，便于检查坐标与项目链接 |
| `gtoPublish --dry-run` | 查看发布任务链，不执行发布任务 |

`build` / `assemble` 不代表单测已经运行；有相关测试时需显式执行。PowerShell 不要通过管道过滤 Gradle 的实时输出后读取 `$LASTEXITCODE`，应先保存输出，再记录退出码。

Spotless 使用 `gradle/spotless/` 中的 Eclipse 4.31 格式规则和导入顺序。`gradle.properties` 的 `jdk.compiler` 导出参数用于 JDK 21 下的未使用导入清理，不要随意删除。网络不可用时，只有依赖已经缓存完整才可使用 `--offline`；首次运行可能需要下载插件及格式化器依赖。

## 依赖与实现位置

- `ae2_version` 指向 [AE2 GTO fork](https://github.com/GregTech-Odyssey/Applied-Energistics-2-gto)，`emi_version` 指向 [EMI GTO fork](https://github.com/GregTech-Odyssey/EMI-gto)，不要替换为同名上游依赖。
- 保留 GuideME、Glodium 前置与既有可选联动。FastCollection 不内嵌到本模组，运行环境需提供它；GTO 整合包由 LeanObject 提供。
- `xmod/emi` 维护电路切割器配方分类、终端配方填充和样板槽交互。AE2 的 EMI 接口由 AE2 fork 提供，不在本仓复制另一份实现。
- 输出总线复用 AE2 的吞吐统计与传输上下文；标签过滤共用物品、流体索引，精确存储复用查询容器，发信器使用 fork 的模糊数量查询。修改这些逻辑时核对对应 fork 当前 API，不恢复逐次全表扫描或无谓分配。
- GTOCore、AE2 中原有的通用 ExtendedAE 补丁已迁入本仓。通用行为在这里维护；Core 专属机器、分级规则与界面仍由 Core 负责。
- 开发环境默认不加载旧版 AppliedE，因为它与 AE2 GTO 的键规范化冲突；保留编译期适配不等于已经验证其运行时兼容。

## Maven 发布

使用 `com.gto.gtopublishgradleplugin`，当前只启用 Maven；GitHub Release、CurseForge 未启用。

- 仓库：`https://gtodyssey.com/maven/releases`
- 坐标：`com.glodblock.github.extendedae:extendedae-forge-1.20.1:<mod_version>`
- 基础制品名为 `extendedae`；插件自动附加加载器与 Minecraft 版本。
- 版本位于 `gradle.properties` 的 `mod_version`，格式为 `x.y.z[-suffix]`，例如 `1.4.1001`。每次正式发布递增版本，不在版本号里再写 Minecraft 或 Forge。

将 `gtodysseyRepositoryUsername`、`gtodysseyRepositoryPassword` 配置在 Gradle 用户目录的 `gradle.properties` 中。设置了 `GRADLE_USER_HOME` 时使用该目录，否则使用 `~/.gradle`；不要将凭据提交到仓库。

本地联调用 `publishToMavenLocal`，共享发布使用 `gtoPublish`。正式发布前依次完成格式化、格式检查、构建及 `gtoValidate`；`gtoPublish` 也会自动执行预检、构建与 Maven 发布。发布制品必须经过 `reobfJar`，不要将开发环境字节码当成正式产物。

在依赖 ExtendedAE 的 GTOCore、AE2 开发环境交付改动前，先确认对应源码与产物可获取，再同步各消费仓库的 `extendedae_version`。本地 Maven 联调仅对本机生效，不能代替共享仓库发布。
