#!/usr/bin/env sh
set -e

cd "$(dirname "$0")"

if ! command -v mvn >/dev/null 2>&1; then
    echo "Erreur : Maven n'est pas disponible avec la commande mvn."
    echo "Installe Maven ou ajoute Maven au PATH."
    exit 1
fi

lancer() {
    echo
    echo "============================================================"
    echo "$2"
    echo "============================================================"
    mvn -pl "$1" -am exec:java </dev/null
}

while true
do
    echo "Demonstration complete du projet Rep-Connais"
    echo "Repertoire du projet : $(pwd)"
    echo
    echo "Choisissez la logique a executer :"
    echo "1 - Logique classique"
    echo "2 - Logique modale"
    echo "3 - Logique des defauts"
    echo "4 - Logique de description"
    echo "5 - Reseaux semantiques"
    echo "6 - Toutes les logiques"
    echo "0 - Quitter"
    echo
    printf "Votre choix : "
    read -r choix

    case "$choix" in
        1)
            if lancer logique_classique "Logique classique"; then
                echo
                echo "Execution terminee avec succes."
            else
                echo
                echo "Erreur : la demonstration a echoue."
            fi
            ;;
        2)
            if lancer logique_modale "Logique modale"; then
                echo
                echo "Execution terminee avec succes."
            else
                echo
                echo "Erreur : la demonstration a echoue."
            fi
            ;;
        3)
            if lancer logique_defauts "Logique des defauts"; then
                echo
                echo "Execution terminee avec succes."
            else
                echo
                echo "Erreur : la demonstration a echoue."
            fi
            ;;
        4)
            if lancer logique_description "Logique de description"; then
                echo
                echo "Execution terminee avec succes."
            else
                echo
                echo "Erreur : la demonstration a echoue."
            fi
            ;;
        5)
            if lancer reseaux_semantiques "Reseaux semantiques"; then
                echo
                echo "Execution terminee avec succes."
            else
                echo
                echo "Erreur : la demonstration a echoue."
            fi
            ;;
        6)
            if lancer logique_classique "Logique classique" &&
               lancer logique_modale "Logique modale" &&
               lancer logique_defauts "Logique des defauts" &&
               lancer logique_description "Logique de description" &&
               lancer reseaux_semantiques "Reseaux semantiques"; then
                echo
                echo "Toutes les demonstrations sont terminees avec succes."
            else
                echo
                echo "Erreur : au moins une demonstration a echoue."
            fi
            ;;
        0)
            echo
            echo "Fermeture du menu."
            exit 0
            ;;
        *)
            echo
            echo "Choix invalide."
            ;;
    esac
done
