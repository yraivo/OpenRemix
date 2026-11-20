[CmdletBinding()]
param([string]$Jdk8Root = $env:REMIX_JDK8)

$ErrorActionPreference = 'Stop'
if (-not $Jdk8Root) { $Jdk8Root = $env:JAVA_HOME }
$javac = Join-Path $Jdk8Root 'bin\javac.exe'
$jar = Join-Path $Jdk8Root 'bin\jar.exe'
if (-not (Test-Path -LiteralPath $javac)) { throw 'Supply -Jdk8Root or REMIX_JDK8 pointing to JDK 8.' }

$outputRoot = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot 'build\javac'))
$classes = [IO.Path]::GetFullPath((Join-Path $outputRoot 'classes'))
if (Test-Path -LiteralPath $classes) {
    $resolved = (Resolve-Path -LiteralPath $classes).Path
    if (-not $resolved.StartsWith($outputRoot + [IO.Path]::DirectorySeparatorChar, [StringComparison]::OrdinalIgnoreCase)) {
        throw 'Unexpected compiler output directory.'
    }
    Remove-Item -LiteralPath $resolved -Recurse -Force
}
New-Item -ItemType Directory -Path $classes -Force | Out-Null
$libraries = Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'libs') -Filter '*.jar' -File | Sort-Object Name
$sources = Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src\main\java') -Filter '*.java' -File -Recurse | Sort-Object FullName
$arguments = @('-encoding', 'UTF-8', '-source', '8', '-target', '8', '-proc:none', '-g', '-classpath', ($libraries.FullName -join ';'), '-d', $classes) + $sources.FullName
$argfile = Join-Path $outputRoot 'javac.args'
$quoted = $arguments | ForEach-Object { '"' + $_.Replace('\', '/') + '"' }
[IO.File]::WriteAllLines($argfile, $quoted, [Text.UTF8Encoding]::new($false))
& $javac '-J-Xmx2g' '-J-Duser.language=en' ('@' + $argfile)
if ($LASTEXITCODE -ne 0) { throw 'javac failed.' }
$artifact = Join-Path $outputRoot 'Remix-1.7.1.jar'
Copy-Item -Path (Join-Path $PSScriptRoot 'src\main\resources\*') -Destination $classes -Recurse -Force
& $jar 'cfe' $artifact 'net.minecraft.client.main.Main' '-C' $classes '.'
if ($LASTEXITCODE -ne 0) { throw 'jar failed.' }
Write-Host "Built $artifact"
