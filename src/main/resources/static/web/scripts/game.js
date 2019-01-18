var myApp = new Vue({
    el: '#app',
    data: {
        numbers: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        letters: ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        data: "",
        gpURL: "",
        ownerGame: "",
        opponent: "",
    },

    methods: {

        getURL: function () {
            var url = new URL(window.location.href);
            this.gpURL = url.searchParams.get("gp");
            this.getData();
            console.log(this.gpURL);

        },

        getData: function () {

            fetch('/api/game_view/' + this.gpURL)
                .then((res) => res.json())
                .then((json) => {
                    this.data = json;
                    console.log(this.data);
                    this.init();

                })
                .catch((err) => {
                    console.log(err);
                    text.append(data.message);
                    console.log("errordimierda");
                })
        },

        init: function () {
            this.printShips();
            this.printPlayerInfo();
            this.printEnemySalvos();
            this.printOwnerSalvos();
        },

        printShips: function () {
            for (var x = 0; x < this.data.ships.length; x++) {
                for (var y = 0; y < this.data.ships[x].position.length; y++) {
                    document.getElementById(this.data.ships[x].position[y]).className += this.data.ships[x].type;
                }
            }

        },

        printEnemySalvos: function () {
            var img = document.createElement("img");
            img.src = "/web/styles/assets/fireGif.gif";

            for (var x = 0; x < this.data.salvoesEnemy.length; x++) {
                for (var y = 0; y < this.data.salvoesEnemy[x].position.length; y++) {
                    var img = document.createElement("img");
                    img.className = "fireGif";
                    img.src = "/web/styles/assets/fireGif.gif";
                    document.getElementById(this.data.salvoesEnemy[x].position[y]).append(img);
                }
            }
        },

        printOwnerSalvos: function(){
            for (var x = 0; x < this.data.salvoesOwner.length; x++) {
                for (var y = 0; y < this.data.salvoesOwner[x].position.length; y++) {
                    var img = document.createElement("img");
                    img.className = "fireGif";
                    img.src = "/web/styles/assets/fireGif.gif";
                    document.getElementById(this.data.salvoesOwner[x].position[y]+"E").append(img);
                }
            }   
        },

        printPlayerInfo: function () {

            for (var x = 0; x < this.data.gameplayers.length; x++) {
                if (this.data.gameplayers[x].id == this.gpURL) {
                    this.ownerGame = this.data.gameplayers[x].player.username;
                } else {
                    this.opponent = this.data.gameplayers[x].player.username;
                }
            }
            console.log("owner " + this.ownerGame);
            console.log("opponent " + this.opponent);
        }
    },



    created: function () {
        this.getURL();

    },

});