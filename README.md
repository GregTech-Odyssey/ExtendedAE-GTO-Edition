# ExtendedAE GTO Edition

本项目是为 [GregTech Odyssey](https://github.com/GregTech-Odyssey) 整合包维护的 ExtendedAE 分支，基于 Minecraft 1.20.1 Forge 版 ExtendedAE，提供扩展样板供应器、接口、终端、总线、无限存储元件等 AE2 扩展内容，并针对整合包调整兼容性与行为。

ExtendedAE 的原作者为 [GlodBlock](https://github.com/GlodBlock)，原项目地址为 [GlodBlock/ExtendedAE](https://github.com/GlodBlock/ExtendedAE)。本版本的修改由 GregTech Odyssey 团队维护，并非原作者发布或支持的版本。

本版本与原版的差异见 [GTO_CHANGES.md](GTO_CHANGES.md)。使用中遇到问题或有功能建议，请反馈至 [GregTech Odyssey Issue tracker](https://github.com/GregTech-Odyssey/GregTech-Odyssey/issues)，并注明使用的是 ExtendedAE GTO Edition。

## 模组信息

- 显示名为 **ExtendedAE GTO Edition**，模组 ID 保持 `expatternprovider`，Java 包名与资源命名空间保持不变。
- 只维护 Forge 1.20.1 版，需要 Java 21，配套使用 [Applied Energistics 2 GTO Edition](https://github.com/GregTech-Odyssey/Applied-Energistics-2-gto) 与 [EMI GTO Edition](https://github.com/GregTech-Odyssey/EMI-gto)。
- 保留 GuideME、Glodium 前置；运行环境还需提供 FastCollection，GTO 整合包中由 LeanObject 提供。
- 本分支替换原版 ExtendedAE，不能同时安装两份。配方查看与填充使用原生 EMI 集成，已移除原版 JEI、REI 集成。

## 原版功能示例

以下图片来自上游项目，GTO 分支的行为差异以上述差异文档为准。

- 36 slots pattern provider

<img alt="epp" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/epp.png?raw=true" width="250" height="304"/>

- 36 slots interface

<img alt="ei" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/2.png?raw=true" width="250" height="304"/>

- infinity water/cobblestone cell
  
<img alt="cell2" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/cell2.png?raw=true" width="180" height="60"/>
<img alt="cell1" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/cell1.png?raw=true" width="200" height="60"/>

- better pattern access terminal

<img alt="cell2" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/pat.png?raw=true" width="270" height="304"/>
<img alt="cell1" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/hl.png?raw=true" width="500" height="304"/>

- 8x faster import/export bus

<img alt="buss" src="https://github.com/GlodBlock/AE2FluidCraft-Rework/blob/pic/buss.png?raw=true" width="200" height="90"/>

## 署名与许可证

- **ExtendedAE**：GlodBlock 及原项目贡献者；保留上游提交历史。
- **贴图与模型**：[Sea_Kerman](https://github.com/Sea-Kerman)。
- **翻译**：gt147532689（简体中文）、nekitbrush（俄语）及其他贡献者。
- **GTO 分支维护与修改**：GregTech Odyssey 团队。

本项目沿用 [GNU LGPL v3.0 许可证](LICENSE.txt)。原作者与贡献者的署名予以保留。

