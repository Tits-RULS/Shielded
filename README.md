# Shielded
Juego para móvil orientado a espadas y escudos

Estructura:

    Menu:
        Vista:
            cover.xml
            menu.xml
            menu_match.xml
            menu_vs.xml
            menu_theme.xml
            menu_dimension.xml
            menu_Activity.java
            IF_pv_menu.java
            *Música
            *Imágenes
        Presentador:
            menu_presenter.java
            IF_vp_menu.java
            IF_mp_menu.java
        Modelo:
            menu_model.java
            IF_pm_menu.java

    Game:
        Vista:
            game.xml
            game_Activity.java
            IF_pv_game.java
            *Imágenes
            *Música
        Presentador:
            game_presentar.java
            IF_vp_game.java
            IF_mp_game.java
        Modelo:
            game_model.java
            IF_pm_game.java
            Data.java
            IA*.java
            Player.java
            Square.java
            Stick.java
            HttpClient.java