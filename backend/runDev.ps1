Param(
    [String] $name
)


Set-Location ../

Write-Output "Directorio actual cambiado a $PWD"

docker build -t ruky00/$name -f ./multistage-dockerfile.Dockerfile .

docker push ruky00/$name
