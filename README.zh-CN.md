<div align="center">

# OpenRemix

**Remix `1.7.1` 非官方反混淆、安全审计与独立可构建 Java 8 源码恢复工程**

[English](README.md) · [简体中文](README.zh-CN.md)

[![Minecraft](https://img.shields.io/badge/Minecraft-1.8-62B47A?style=flat-square)](https://www.minecraft.net/)
[![Java 8](https://img.shields.io/badge/Java-8%20(52.0)-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Sources](https://img.shields.io/badge/Java%20Sources-3%2C321%20Files-2ea44f?style=flat-square)](src/main/java/)
[![Build](https://img.shields.io/badge/Build-Plain%20Gradle%20%2F%20javac-0969da?style=flat-square)](build.gradle)

<br />

<img src="docs/images/splash.png" alt="OpenRemix 启动界面" width="49%" />
<img src="docs/images/clickgui.jpg" alt="OpenRemix 游戏内 ClickGUI" width="49%" />

</div>

---

## 项目简介

**OpenRemix** 是针对 Minecraft `1.8` 客户端 **Remix `1.7.1`** 的非官方逆向与源码恢复工程。

原始样本为历史混淆 JAR，包含外层 attach/loader 加载壳、`12,068` 份伪装成 `.class` 的 masked-gzip 隐藏载荷、字符串与常量混淆，以及破坏性反分析与旧授权回调。本仓库提供最终收敛后的 **标准 Java 8 Gradle / `javac` 源码工程**（`3,321` 份 `.java`）——无需原版混淆 JAR、无需映射表、无需任何字节码后处理，直接编译即可产出客户端 JAR。

### 核心成果

- **独立纯源码构建（`3,321` 份 `.java` → `3,538` 个 class）**：已移除全部 `36,209` 份保护层生成文件、隐藏载荷与历史 ABI 后处理链，标准 Gradle（`gradlew clean build`）或 JDK 8 原生 `javac`（`Build-Plain.ps1`）直接编译产出 `Remix-1.7.1.jar`。
- **分包命名与核心模块可读化**：全树 `Class_<hash>` 文件数为 `0`、1~2 字符混淆类名为 `0`、`field_<hash>` 为 `0`；核心业务目录 `remix/module/`（`162` 文件、`94` 个模块）具备良好可读性，仅 `4` 个文件命中 `6` 处 `method_<hash>`。
- **Java 层与隐藏载荷入口审计**：剥离外层 attach/native 壳（`95` 条目），处置破坏性授权失败回调、动物构造路径线程炸弹、旧远程遥测与授权门禁，加固命令/路径/TLS 边界，并锁定 `43` 份运行依赖（其中升级了 Log4j `2.26.1`、Netty `4.1.140.Final`、Guava、Gson、Commons、HttpClient 等）。
- **运行时缺陷修复**：修复了存档落盘、Netty direct `ByteBuf` 解码、OptiFine CTM 纹理索引、ClickGUI/HUD/服务端命令注册、移动包坐标门控、FullBright/Xray 亮度所有权、Scaffold 与 MotionBlur 等问题。
- **技术 Write-up**：
  - [`docs/Remix-1.6.3-Recovery-WP.zh-CN.md`](docs/Remix-1.6.3-Recovery-WP.zh-CN.md) — `1.6.3` 从零搭建恢复体系技术文档
  - [`docs/Remix-1.7.1-Recovery-WP.zh-CN.md`](docs/Remix-1.7.1-Recovery-WP.zh-CN.md) — `1.7.1` 跨版本迁移、入口审计与独立构建技术文档

---

## 源码实测指标

| 检查项 | 实测结果 | 说明 |
| :--- | :--- | :--- |
| 保护层与 ABI 后处理 | **已移除** | 不再包含 `o/` `g/` 生成文件、masked-gzip 载荷与编译后改写阶段 |
| `Class_<hash>` 与 1~2 字符混淆类名 | **`0`** | `3,321` 个 Java 文件均已按职责分包命名 |
| `field_<hash>` 混淆字段 | **`0`** | 全树清零 |
| `remix/module/`（`162` 文件）中的 `method_<hash>` | **`4` 文件共 `6` 处** | 核心模块实现可直接阅读与修改 |
| 全树（`3,321` 文件）剩余 `method_<hash>` | **`989` 文件共 `1,391` 个声明** | `remix/` 占 `451` 个（多为旧混淆器遗留的静态哨兵 getter 与已禁用旧认证辅助方法） |
| MCP SRG 名（`func_*` / `field_*`） | **`remix/` 内为 `0`** | 仅存在于 `net/`、`optifine/`、`shadersmod/` |
| 编译器与旧框架兼容层 | **以普通 Java 保留** | `remix.compat.compiler`（`JvmBooleanSlots`、`JvmThrowTypes`）与已禁用的 `remix.compat.legacy.*` |
| 反编译器注释 | **导出时已删除** | 已删除 Vineflower 反编译器注释（含部分控制流与同步结构提示），未改动代码逻辑 |

> **关于符号命名**：原始混淆二进制已不可逆地丢弃了原作者变量名与注释。本仓库标识符基于 MCP `1.8`、OptiFine、字节码结构与调用语义重建。

---

## 验收范围对照

| 产物版本 | 已实测覆盖范围 | 未宣称覆盖的范围 |
| :--- | :--- | :--- |
| **`1.6.3` 审计基线**<br>*（过程见 1.6.3 技术文档）* | `25,004` 份源码 + `119` 步后处理；普通世界与村庄生成、`625` 个磁盘区块重读、重复保存退出、ClickGUI/配置、本机协议 `47` TCP + 双向 zlib、多人游玩观察 | 全部 `86` 模块行为、在线正版认证、第三方原生库逐指令审计 |
| **`1.6.3` LegacyPing 增量修复** | 定向 Netty pipeline 回归（`18` 用例 / `154` 断言），修复 backing-array 与出站提前释放问题；独立于此前已安装的 `1.6.3` 验收基线单独验证 | 任意 TCP 分片与完整服务器列表回退 |
| **`1.7.1` 独立源码构建**<br>*（本仓库主分支）* | JDK 8 标准 Gradle 与纯 `javac` 双路构建（`3,321` 份 `.java` → `3,538` 个 class）；本地协议 `47` 测试服连接（注册 `94` 模块）、事件总线、Speed/TargetStrafe、语言/统计/ClickGUI/创造栏、配置容错、FullBright/Xray/MotionBlur、单人世界创建 + `/time set 1000` + 保存退出 | 全部 `94` 模块组合、外部在线正版认证、任意第三方材质/光影包、第三方原生二进制深审（如 LWJGL/JNA） |

---

## 从源码构建

### 环境要求
- **JDK 8**（64 位，推荐 [Adoptium Temurin 8](https://adoptium.net/temurin/releases/?version=8)）

### 方式一：Gradle 构建（推荐）

Windows PowerShell：

```powershell
$env:JAVA_HOME = 'C:\path\to\jdk8'
.\gradlew.bat clean build
```

Linux / macOS：

```bash
export JAVA_HOME=/path/to/jdk8
./gradlew clean build
```

- 产物：`build/libs/Remix-1.7.1.jar`
- 全部 `43` 份应用依赖已随仓库放在 `libs/`（清单见 [`DEPENDENCIES.txt`](DEPENDENCIES.txt)）。Gradle Wrapper 首次下载 Gradle `8.12` 后，后续可加 `--offline` 离线构建。

### 方式二：纯 `javac` + `jar` 构建（无需 Gradle）

Windows PowerShell：

```powershell
.\Build-Plain.ps1 -Jdk8Root 'C:\path\to\jdk8'
```

- 产物：`build/javac/Remix-1.7.1.jar`

### 运行说明与注意事项

- **启动入口**：`net.minecraft.client.main.Main`。实际启动游戏仍需通过 Minecraft 启动器准备 `1.8` 原版资源文件（`assets`）、对应平台的 LWJGL / JInput 原生库以及 `libs/` 中的 `43` 份依赖。
- **明文凭据保存提示**：为保持原版客户端既有行为，若在界面中保存账号或代理配置，程序会将已填密码以**明文**写入运行目录下的 `Accounts.txt`（`remix/config/AccountsTextConfig.java`）与 `Proxies.txt`（`remix/config/ProxiesTextConfig.java`）。本仓库不包含这些运行文件，请勿在其中存放敏感凭据。
- **第三方原生库边界**：安全审计聚焦于应用层 Java 类与隐藏载荷；第三方原生库（LWJGL / JInput 及依赖中 JNA 自带的原生二进制）主要核对来源与差分，未做逐指令逆向审计。

---

## 目录结构

```text
OpenRemix/
├── src/main/java/
│   ├── net/minecraft/         # 2,281 份 Minecraft 1.8 客户端、内置服务端与协议代码
│   ├── remix/                 # 861 份 Remix 客户端源码
│   │   ├── client/            # 启动装配与客户端入口（ClientStartup、RemixClient）
│   │   ├── module/            # 按功能分类的 94 个客户端模块（162 文件）
│   │   ├── event/             # 事件总线与订阅机制
│   │   ├── command/           # 客户端命令
│   │   ├── config/ & setting/ # 配置持久化与模块设置
│   │   ├── ui/                # ClickGUI、TabGUI、HUD、账号与代理界面
│   │   ├── security/          # 路径、TLS、命令与危险调用拦截护栏
│   │   └── compat/            # 普通 Java 兼容适配层与已禁用的旧认证框架
│   ├── optifine/              # 114 份 OptiFine 渲染扩展
│   ├── shadersmod/            # 50 份 ShadersMod 光影扩展
│   └── com/                   # 15 份第三方 API 适配类
├── src/main/resources/        # 客户端资源与着色器
├── libs/                      # 43 份锁定的编译/运行依赖（含 2 份本地兼容适配的 Mojang 派生库）
├── docs/
│   ├── images/                # 实机截图目录
│   ├── Remix-1.6.3-Recovery-WP.zh-CN.md
│   └── Remix-1.7.1-Recovery-WP.zh-CN.md
├── build.gradle               # 标准 Gradle Java 8 构建脚本
├── Build-Plain.ps1            # 零 Gradle 纯 javac 构建脚本
└── DEPENDENCIES.txt           # 43 份依赖版本清单
```

---

## 关于 Remix `1.6.3`

整套恢复方法论最早在 **Remix `1.6.3`** 上建立并验证（`25,004` 份源码 + `119` 步后处理）。由于 `1.6.3` 未推进到全树语义重命名与独立纯源码构建，主分支未纳入其代码树；完整恢复过程、安全处置与排错记录保存在 [`docs/Remix-1.6.3-Recovery-WP.zh-CN.md`](docs/Remix-1.6.3-Recovery-WP.zh-CN.md)。

---

## 免责声明

- 本仓库**仅供技术学习、安全审计与 Java 反混淆研究使用**。
- 本仓库不分发任何原始混淆二进制包，亦不对 Minecraft、OptiFine、ShadersMod、第三方字体/依赖库或原始 Remix 代码主张版权所有权。
- 在未获授权的服务器使用修改版客户端违反绝大多数服务器规则与服务条款，一切后果由使用者自行承担。
