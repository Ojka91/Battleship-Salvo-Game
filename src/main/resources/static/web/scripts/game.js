var myApp = new Vue({
    el: '#app',
    data: {
        numbers: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        letters: ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        data: "",
        gpURL: "",
        ownerGame: "",
        opponent: "",
        starting: 0,


        shipAlign: 1, //0 = horizontal, 1 = vertical

        shipButtons: {
            carrier: 0,
            battleShip: 0,
            submarine: 0,
            destructor: 0,
            patrol: 0,
        },
        shipLength: {
            carrier: 5,
            battleShip: 4,
            submarine: 3,
            destructor: 3,
            patrol: 2,
        },
        shipName: "",
        shipToPlace: 0,
        shipPlacing: false,
        shipPosition: [{
                shipType: "destructor",
                shipPosition: ["A1", "A2", "A3"]
            },
            {
                shipType: "carrier",
                shipPosition: ["C3", "D3", "E3", "F3", "G3"]
            },
        ]

    },

    methods: {

        getURL: function () {
            var url = new URL(window.location.href);
            this.gpURL = url.searchParams.get("gp");
            this.getData();
            console.log(this.gpURL);

        },

        placeShips(id) {
            if (!document.getElementById(id).classList.contains("btn-secondary")) {
                console.log("SHIP: " + id);
                myApp.shipName = id;
                shipName = document.getElementById(id);
                myApp.shipButtons[id] == 1;

                carrier = document.getElementById("carrier").classList.remove("btn-warning");
                carrier = document.getElementById("carrier").classList.add("btn-primary");
                battleShip = document.getElementById("battleShip").classList.remove("btn-warning");
                battleShip = document.getElementById("battleShip").classList.add("btn-primary");
                submarine = document.getElementById("submarine").classList.remove("btn-warning");
                submarine = document.getElementById("submarine").classList.add("btn-primary");
                destructor = document.getElementById("destructor").classList.remove("btn-warning");
                destructor = document.getElementById("destructor").classList.add("btn-primary");
                patrol = document.getElementById("patrol").classList.remove("btn-warning");
                patrol = document.getElementById("patrol").classList.add("btn-primary");

                shipName.classList.remove("btn-primary");
                shipName.classList.add("btn-warning");

                if (!document.getElementById(id).classList.contains("btn-secondary")) {
                    myApp.shipPlacing = true;
                    myApp.shipToPlace = myApp.shipLength[id];

                }
            }





        },
        rotateShip() {
            if (myApp.shipAlign == 0) {
                myApp.shipAlign = 1;
            } else {
                myApp.shipAlign = 0;
            }
        },

        placeAShip(letters, numbers) {
            if (myApp.shipPlacing == true) {
                //shipAlign 0 == horitzontal
                if (myApp.shipAlign == 0) {
                    for (var x = 0; x < myApp.shipToPlace; x++) {
                        document.getElementById(letters + (numbers + x)).classList.add("carrier");
                        document.getElementById(myApp.shipName).classList.remove("btn-warning");
                        document.getElementById(myApp.shipName).classList.add("btn-secondary");
                        myApp.shipPlacing = false;
                    }
                } else {

                    for (var y = 0; y < myApp.letters.length; y++) {
                        if (myApp.letters[y] == letters) {
                            myApp.starting = y;


                        }
                    }
                    for (var z = 0; z < myApp.shipToPlace; z++) {
                        document.getElementById((myApp.letters[myApp.starting + z]) + numbers).classList.add("carrier");
                        document.getElementById(myApp.shipName).classList.add("btn-secondary");
                        document.getElementById(myApp.shipName).classList.remove("btn-warning");
                        myApp.shipPlacing = false;

                    }
                }

            }

        },

        sound() {
            if (document.getElementById("audio").muted == false) {
                document.getElementById('audio').muted = true;

            } else {
                document.getElementById("audio").muted = false;
            }

        },

        getShips() {
            fetch('/api/games/players/' + myApp.gpURL + '/ships', {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(myApp.shipPosition)
            }).then(function (response) {
                return response.json();
            }).then(function (json) {
                console.log('parsed json', json)
                myApp.getData();
            }).catch(function (ex) {
                console.log('parsing failed', ex)
                alert("error signing up" + ex);

            });

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
                    alert("don't cheat bitch: ");
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
            if (this.data.shipsOwner != null) {
                for (var x = 0; x < this.data.shipsOwner.length; x++) {
                    for (var y = 0; y < this.data.shipsOwner[x].position.length; y++) {
                        document.getElementById(this.data.shipsOwner[x].position[y]).className += this.data.shipsOwner[x].type;
                    }
                }

            } else {

            }

        },

        printEnemySalvos: function () {
            if (this.data.salvoesEnemy != null) {
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
                        if (document.getElementById(this.data.salvoesEnemy[x].position[y]).classList.contains("submarine" || "carrier" || "patrolBoat" || "destroyer")) {
                            document.getElementById(this.data.salvoesEnemy[x].position[y]).append(img);
                        } else {
                            document.getElementById(this.data.salvoesEnemy[x].position[y]).append(img2);
                        }
                    }
                }

            }
        },

        printOwnerSalvos: function () {
            if (this.data.salvoesOwner != null) {
                for (var x = 0; x < this.data.salvoesOwner.length; x++) {
                    for (var y = 0; y < this.data.salvoesOwner[x].position.length; y++) {
                        var img = document.createElement("img");
                        img.className = "fireGif";
                        img.src = "/web/styles/assets/fireGif.gif";
                        document.getElementById(this.data.salvoesOwner[x].position[y] + "E").innerHTML = this.data.salvoesOwner[x].turn;
                        document.getElementById(this.data.salvoesOwner[x].position[y] + "E").append(img);
                    }
                }

            }
        },

        printPlayerInfo: function () {
            if (this.data.gameplayers.length > 1) {
                for (var x = 0; x < this.data.gameplayers.length; x++) {

                    if (this.data.gameplayers[x].id == this.gpURL) {
                        this.ownerGame = this.data.gameplayers[x].player.email;
                    }
                    if (this.data.gameplayers[x].id != this.gpURL) {
                        this.opponent = this.data.gameplayers[x].player.email;
                    }

                }

            } else {
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