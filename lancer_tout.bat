@echo off
setlocal EnableExtensions
chcp 65001 > nul 2> nul

pushd "%~dp0"
if errorlevel 1 goto erreur_dossier

where mvn > nul 2> nul
if errorlevel 1 goto erreur_maven

:menu
echo Demonstration complete du projet Rep-Connais
echo Repertoire du projet : %CD%
echo.
echo Choisissez la logique a executer :
echo 1 - Logique classique
echo 2 - Logique modale
echo 3 - Logique des defauts
echo 4 - Logique de description
echo 5 - Reseaux semantiques
echo 6 - Toutes les logiques
echo 0 - Quitter
echo.
set "choix="
set /p "choix=Votre choix : "
if errorlevel 1 goto quitter

if "%choix%"=="1" (
    call :executer logique_classique "Logique classique"
    goto menu
)

if "%choix%"=="2" (
    call :executer logique_modale "Logique modale"
    goto menu
)

if "%choix%"=="3" (
    call :executer logique_defauts "Logique des defauts"
    goto menu
)

if "%choix%"=="4" (
    call :executer logique_description "Logique de description"
    goto menu
)

if "%choix%"=="5" (
    call :executer reseaux_semantiques "Reseaux semantiques"
    goto menu
)

if "%choix%"=="6" (
    call :executer_tout
    goto menu
)

if "%choix%"=="0" goto quitter

echo.
echo Choix invalide.
goto menu

:executer
echo.
echo ============================================================
echo %~2
echo ============================================================
cmd /c "mvn -pl %~1 -am exec:java < nul"
if errorlevel 1 (
    echo.
    echo Erreur : la demonstration a echoue.
) else (
    echo.
    echo Execution terminee avec succes.
)
exit /b 0

:executer_tout
set "erreur=0"
call :lancer logique_classique "Logique classique"
if errorlevel 1 set "erreur=1"
if "%erreur%"=="0" call :lancer logique_modale "Logique modale"
if errorlevel 1 set "erreur=1"
if "%erreur%"=="0" call :lancer logique_defauts "Logique des defauts"
if errorlevel 1 set "erreur=1"
if "%erreur%"=="0" call :lancer logique_description "Logique de description"
if errorlevel 1 set "erreur=1"
if "%erreur%"=="0" call :lancer reseaux_semantiques "Reseaux semantiques"
if errorlevel 1 set "erreur=1"
echo.
if "%erreur%"=="0" (
    echo Toutes les demonstrations sont terminees avec succes.
) else (
    echo Erreur : au moins une demonstration a echoue.
)
exit /b 0

:lancer
echo.
echo ============================================================
echo %~2
echo ============================================================
cmd /c "mvn -pl %~1 -am exec:java < nul"
exit /b %ERRORLEVEL%

:quitter
echo.
echo Fermeture du menu.
popd
endlocal
exit /b 0

:erreur_maven
echo Erreur : Maven n'est pas disponible avec la commande mvn.
echo Installe Maven ou ajoute Maven au PATH de Windows.
popd
endlocal
exit /b 1

:erreur_dossier
echo Erreur : impossible d'ouvrir le dossier du projet.
endlocal
exit /b 1
