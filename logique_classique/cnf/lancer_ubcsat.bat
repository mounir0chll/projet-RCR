@echo off
chcp 65001 > nul
echo Copier ubcsat.exe dans ce dossier avant de lancer ce script.
echo.
ubcsat.exe -alg saps -i alarme_satisfiable.cnf -solve
echo.
ubcsat.exe -alg saps -i alarme_inference_evacuation.cnf -solve
echo.
ubcsat.exe -alg saps -i contradiction_simple.cnf -solve
