@echo off

set target_dir=src/protoGen

:: 删除旧生成的目录
rd /s /q src\protoGen

:: Usage:
::  java WireCompiler --proto_path=<path> --java_out=<path>
::     [--files=<protos.include>]
::     [--includes=<message_name>[,<message_name>...]]
::     [--excludes=<message_name>[,<message_name>...]]
::     [--quiet]
::     [--dry_run]
::     [--android]
::     [--compact]
::     [file [file...]]
::
:: If the --includes flag is present, its argument must be a comma-separated list of fully-qualified message or enum names. The output will be limited to those messages and enums that are (transitive) dependencies of the listed names. The --excludes flag excludes types, and takes precedence over --includes.
:: If the --registry_class flag is present, its argument must be a Java class name. A class with the given name will be generated, containing a constant list of all extension classes generated during the compile. This list is suitable for passing to Wire's constructor at runtime for constructing its internal extension registry.
:: If --quiet is specified, diagnostic messages to stdout are suppressed.
:: The --dry_run flag causes the compile to just emit the names of the source files that would be generated to stdout.
:: The --android flag will cause all messages to implement the Parcelable interface.
:: The --compact flag will emit code that uses reflection for reading, writing, and toString methods which are normally implemented with code generation.

java -jar ./lib/wire-compiler-2.3.0-RC1-jar-with-dependencies.jar ^
--proto_path=src/proto ^
--java_out=src menglei/test.proto menglei/protos.proto ^
--compact


echo "=================== to android ======================"
set android_path=E:\ProgramFiles\Android\workspace\MyApplication\app\src\main\java

java -jar ./lib/wire-compiler-2.3.0-RC1-jar-with-dependencies.jar ^
--proto_path=src/proto ^
--java_out=%android_path% menglei/android_protos.proto ^
--compact

pause
