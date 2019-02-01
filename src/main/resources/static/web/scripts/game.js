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

        sound(){
            if(document.getElementById("audio").muted == false){
                document.getElementById('audio').muted = true;

            }
            
                
            
            else{
                document.getElementById("audio").muted = false;
                }
               
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
                    //    text.append(data.message);
                    alert("don't cheat bitch: " +err);
                    window.location.href = "games.html";
                    console.log(err);
                })
        },

        init: function () {
            this.printShips();
            this.printPlayerInfo();
            this.printEnemySalvos();
            this.printOwnerSalvos();
        },

        printShips: function () {
            if(this.data.shipsOwner!=null){
                for (var x = 0; x < this.data.shipsOwner.length; x++) {
                    for (var y = 0; y < this.data.shipsOwner[x].position.length; y++) {
                        document.getElementById(this.data.shipsOwner[x].position[y]).className += this.data.shipsOwner[x].type;
                    }
                }

            }
            else{

            }

        },

        printEnemySalvos: function () {
            if(this.data.salvoesEnemy!=null){
                var img = document.createElement("img");
                img.src = "/web/styles/assets/fireGif.gif";
    
                for (var x = 0; x < this.data.salvoesEnemy.length; x++) {
                    for (var y = 0; y < this.data.salvoesEnemy[x].position.length; y++) {
                        var img = document.createElement("img");
                        var img2 = document.createElement("img");
                        img.className = "fireGif";
                        img2.className = "waterSplash"
                        img.src = "/web/styles/assets/fireGif.gif";
                        img2.src = "/web/styles/assets/splash.gif";
                        document.getElementById(this.data.salvoesEnemy[x].position[y]).innerHTML = this.data.salvoesEnemy[x].turn;
                        if (document.getElementById(this.data.salvoesEnemy[x].position[y]).classList.contains("submarine" || "carrier" || "patrolBoat" || "destroyer")){
                            document.getElementById(this.data.salvoesEnemy[x].position[y]).append(img);
                        }
                        else{
                            document.getElementById(this.data.salvoesEnemy[x].position[y]).append(img2);
                        }
                    }
                }

            }
        },

        printOwnerSalvos: function () {
            if(this.data.salvoesOwner!=null) {
                for (var x = 0; x < this.data.salvoesOwner.length; x++) {
                    for (var y = 0; y < this.data.salvoesOwner[x].position.length; y++) {
                        var img = document.createElement("img");
                        img.className = "fireGif";
                        img.src = "/web/styles/assets/fireGif.gif";
                        document.getElementById(this.data.salvoesOwner[x].position[y]+"E").innerHTML = this.data.salvoesOwner[x].turn;
                        document.getElementById(this.data.salvoesOwner[x].position[y] + "E").append(img);
                    }
                }

            }
        },

        printPlayerInfo: function () {
            if (this.data.gameplayers.length > 1){
            for (var x = 0; x < this.data.gameplayers.length; x++) {
             
                    if (this.data.gameplayers[x].id == this.gpURL) {
                        this.ownerGame = this.data.gameplayers[x].player.email;
                    } if (this.data.gameplayers[x].id != this.gpURL) {
                        this.opponent = this.data.gameplayers[x].player.email;
                    }

                }
                
            }
            else{
                this.ownerGame = this.data.gameplayers[0].player.email;  
                this.opponent = "waiting for an opponent";
            }
            console.log("owner " + this.ownerGame);
            console.log("opponent " + this.opponent);
        }
    },



    created: function () {
        this.getURL();

    },

});