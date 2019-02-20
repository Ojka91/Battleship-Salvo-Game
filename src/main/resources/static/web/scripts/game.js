var myApp = new Vue({
    el: '#app',
    data: {
        numbers: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        letters: ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        data: "",
        gpURL: "",
        ownerGame: "",
        opponent: "",
        turn: 2,
        enemyInfo: "",

        salvoPosition: [],
        salvoInfo: [],
        firesLeft: 5,
        turnArray: [],
        firing: false,
        firesReady: false,
        //---ship placement variables ---//
        starting: 0, //start point mouse horizontal direction
        shipsDone: false, //check out if has to place ships
        //ship Orientation
        shipAlign: 1, //0 = vertical, 1 = horizontal
        shipCollision: [], //check out if ship can be placed

        //which ship I'm currently placing
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
        shipToPlace: 0, //length ship placing
        shipPlacing: false, //trigger if can place a ship

        //object whith ship info for ajax post
        shipPosition: []
        //----------------------------------------//
    },

    methods: {
        //get current url for making correct ajax call
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
                    myApp.salvoInfo = [];
                    myApp.init();
                    this.enemyInfo = Object.entries(myApp.data.sinkedEnemy);

                })
                .catch((err) => {
                    //    text.append(data.message);
                    console.log(err);
                    //alert("don't cheat bitch: " + err);
                    // window.location.href = "games.html";
                })
        },


        init: function () {
            this.printShips();
            this.printPlayerInfo();
            this.printEnemySalvos();
            this.printOwnerSalvos();
        },
        lifeInfoGame() {
            // for (var key in myApp.data.sinkedEnemy){
            //     var name = JSON.stringify()

            // }
            myApp.data.sinkedEnemy.array.forEach(element => {

            });
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
        },

        //activates and desactivates music
        sound() {
            if (document.getElementById("audio").muted == false) {
                document.getElementById('audio').muted = true;

            } else {
                document.getElementById("audio").muted = false;
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
        fireOver(letters, numbers) {
            document.getElementById(letters + numbers + "E").classList.add("fireOver");
        },
        fireOut(letters, numbers) {
            document.getElementById(letters + numbers + "E").classList.remove("fireOver");
        },

        printShips: function () {
            if (this.data.shipsOwner != null) {
                console.log("inside printing");
                for (var x = 0; x < this.data.shipsOwner.length; x++) {
                    for (var y = 0; y < this.data.shipsOwner[x].position.length; y++) {
                        document.getElementById(this.data.shipsOwner[x].position[y]).className = this.data.shipsOwner[x].type;
                    }
                }
                myApp.shipsDone = true;
                myApp.firesReady = true;
            } else {
                myApp.shipsDone = false;
            }

        },

        placeFires(letters, numbers) {
      

            

         

                if (myApp.firesLeft > 0) {
                    if (!myApp.salvoPosition.includes(letters + numbers)) {
                        if (letters != "" && numbers != "") {
                            var gif = document.getElementsByClassName("fireGif");
                            if (!document.getElementById(letters + numbers + "E").classList.contains("fired")) {
                                console.log(document.getElementById(letters + numbers + "E").classList.contains("fired"))
                                myApp.salvoPosition.push(letters + numbers);
    
                                var img = document.createElement("img");
                                img.className = "fireGif";
                                img.src = "/web/styles/assets/fireGif.gif";
                                document.getElementById(letters + numbers + "E").append(img);
                                document.getElementById(letters + numbers + "E").classList.add("fired");
                                myApp.firesLeft -= 1;
    
                            } else {
                                alert("you already placed there");
                            }
    
                        } else {
                            alert("can't place there there");
                        }
    
    
                    } else {
                        alert("you already placed there");
                    }
                } else {
                    alert("no more shots, FIRE them!");
    
                }
         

        },

        getSalvoData() {
            myApp.salvoInfo = [{
                salvoPosition: myApp.salvoPosition
            }];

            fetch('/api/games/players/' + myApp.gpURL + '/salvos', {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(myApp.salvoInfo)
            }).then(function (response) {
                return response.json();

            }).then(function (json) {
                console.log('parsed json', json)
                alert(json.error);
                myApp.getData();
              

            }).catch(function (ex) {
                console.log('parsing failed', ex)
                alert("error posting ships" + ex);


            });


        },

        printOwnerSalvos: function () {
            if (document.getElementsByClassName("fireGif" != null)) {
                var imgDelete = document.getElementsByClassName("fireGif");
                while (imgDelete.length > 0) {
                    imgDelete[0].parentNode.removeChild(imgDelete[0]);
                    document.getElementsByClassName("fireGif").innerHTML = "";
                }
                console.log("in");

            }

            if (this.data.salvoesOwner != null) {

                console.log("inside print salvos");
                for (var x = 0; x < this.data.salvoesOwner.length; x++) {
                    for (var y = 0; y < this.data.salvoesOwner[x].position.length; y++) {

                        var img = document.createElement("img");
                        img.className = "fireGif";
                        img.src = "/web/styles/assets/fireGif.gif";
                        document.getElementById(this.data.salvoesOwner[x].position[y] + "E").innerHTML = this.data.salvoesOwner[x].turn;
                        document.getElementById(this.data.salvoesOwner[x].position[y] + "E").append(img);
                        document.getElementById(this.data.salvoesOwner[x].position[y] + "E").classList.add("fired");
                    }
                }

            }
        },


        //post the ship positions to the backend to store the data
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
                alert("error posting ships" + ex);

            });

        },

        //-----------------------ship placement functions--------------------//



        //function rotate ship
        rotateShip() {
            if (myApp.shipAlign == 0) {
                myApp.shipAlign = 1;
            } else {
                myApp.shipAlign = 0;
            }
        },

        //declare which ship are currently placing from the buttons comparing the classes
        placeShips(id) {
            if (!document.getElementById(id).classList.contains("btn-secondary")) {

                myApp.shipName = id;
                shipName = document.getElementById(id);


                //first all buttons must have primary class
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

                //then the selected button needs to change the class
                shipName.classList.remove("btn-primary");
                shipName.classList.add("btn-warning");

                //check the length of the ship and activate placing variable              
                myApp.shipPlacing = true;
                myApp.shipToPlace = myApp.shipLength[id];
            }
        },

        //hover function placing ships
        mouseOver(letters, numbers) {
            myApp.shipCollision = []; //reset array
            if (myApp.shipPlacing == true) { //check if can place a ship

                //position vertical ships (shipAlign = 0)
                if (myApp.shipAlign == 0) {

                    //get the ship length and compare each position if can be placed (push 0) or 
                    //it's a invalid position (push 1)
                    for (var x = 0; x < myApp.shipToPlace; x++) {
                        if (numbers == "" || letters == "") {
                            myApp.shipCollision.push(1);
                        } else {
                            console.log(numbers);
                            if (document.getElementById(letters + (numbers + myApp.shipToPlace - 1)) != null && document.getElementById(letters + (numbers + x)).classList.contains("occupied")) {
                                myApp.shipCollision.push(1);
                            } else {
                                myApp.shipCollision.push(0);
                            }
                        }
                    }
                    //check if its out of the grid and compare if the array has any 1
                    for (var x = 0; x < myApp.shipToPlace; x++) {
                        console.log(numbers, numbers + x);

                        if (numbers != "") {
                            //if everything correct print the color
                            if (document.getElementById(letters + (numbers + myApp.shipToPlace - 1)) != null && !document.getElementById(letters + (numbers + x)).classList.contains("occupied") && !myApp.shipCollision.includes(1)) {
                                document.getElementById(letters + (numbers + x)).classList.add(myApp.shipName);


                            } else {
                                myApp.shipCollision.push(1);
                            }
                        }


                    }
                } else {

                    //position horizontal ships (shipAlign = 1)

                    //get the starting point (where the mouse is)
                    for (var y = 0; y < myApp.letters.length; y++) {
                        if (myApp.letters[y] == letters) {
                            myApp.starting = y;
                        }
                    }

                    //repeat same conditions
                    for (var x = 0; x < myApp.shipToPlace; x++) {

                        if (letters == "" || numbers == "") {
                            myApp.shipCollision.push(1);
                        }

                        if (document.getElementById((myApp.letters[myApp.starting + myApp.shipToPlace - 1]) + numbers) != null && document.getElementById((myApp.letters[myApp.starting + x]) + numbers).classList.contains("occupied")) {

                            myApp.shipCollision.push(1);
                        } else {

                            myApp.shipCollision.push(0);
                        }
                    }
                    for (var z = 0; z < myApp.shipToPlace; z++) {

                        if (document.getElementById((myApp.letters[myApp.starting + myApp.shipToPlace - 1]) + numbers) != null && !document.getElementById(letters + numbers).classList.contains("occupied") && !myApp.shipCollision.includes(1)) {
                            document.getElementById((myApp.letters[myApp.starting + z]) + numbers).classList.add(myApp.shipName);

                        } else {
                            myApp.shipCollision.push(1);
                        }

                    }
                }
            }


        },

        //clean statements when mouse move out of a grid
        mouseOut(letters, numbers) {
            if (myApp.shipPlacing == true && numbers != "") {
                //position vertical ships (shipAlign = 0)
                if (myApp.shipAlign == 0 && document.getElementById(letters + (numbers + myApp.shipToPlace - 1)) != null) {
                    for (var x = 0; x < myApp.shipToPlace; x++) {
                        document.getElementById(letters + (numbers + x)).classList.remove(myApp.shipName);
                    }
                } else {
                    //position horizontal ships (shipAlign = 1)
                    for (var y = 0; y < myApp.letters.length; y++) {
                        if (myApp.letters[y] == letters) {
                            myApp.starting = y;
                        }
                    }
                    if (document.getElementById((myApp.letters[myApp.starting + myApp.shipToPlace - 1]) + numbers) != null) {

                        for (var z = 0; z < myApp.shipToPlace; z++) {
                            document.getElementById((myApp.letters[myApp.starting + z]) + numbers).classList.remove(myApp.shipName);
                        }
                    }
                }

            }

        },

        //place a ship on the current position
        placeAShip(letters, numbers) {
            if (myApp.shipPlacing == true) {
                //position vertical ships (shipAlign = 0)
                if (myApp.shipAlign == 0) {
                    console.log(myApp.numbers[numbers + 1 + myApp.shipToPlace])
                    if (!myApp.shipCollision.includes(1)) {
                        var spv = []; //creates an array that will be pushed with ship info for ajax call

                        for (var x = 0; x < myApp.shipToPlace; x++) {
                            document.getElementById(letters + (numbers + x)).classList.add(myApp.shipName, "occupied");
                            document.getElementById(myApp.shipName).classList.remove("btn-warning");
                            //sets another class to the button
                            document.getElementById(myApp.shipName).classList.add("btn-secondary");
                            myApp.shipPlacing = false; //and return to the false statmenet to start again
                            spv.push(letters + (numbers + x))
                        }
                        myApp.shipButtons[myApp.shipName] = 1;
                        myApp.shipPosition.push({
                            shipType: myApp.shipName,
                            shipPosition: spv
                        }) //push ship info 

                    } else {

                    }
                } else {
                    //position horizontal ships (shipAlign = 1)
                    for (var y = 0; y < myApp.letters.length; y++) {
                        if (myApp.letters[y] == letters) {
                            myApp.starting = y;


                        }
                    }
                    if (!myApp.shipCollision.includes(1)) {
                        var sph = [];
                        for (var z = 0; z < myApp.shipToPlace; z++) {
                            document.getElementById((myApp.letters[myApp.starting + z]) + numbers).classList.add(myApp.shipName, "occupied");
                            document.getElementById(myApp.shipName).classList.add("btn-secondary");
                            document.getElementById(myApp.shipName).classList.remove("btn-warning");
                            myApp.shipPlacing = false;
                            sph.push((myApp.letters[myApp.starting + z]) + numbers);
                        }
                        myApp.shipButtons[myApp.shipName] = 1;
                        myApp.shipPosition.push({
                            shipType: myApp.shipName,
                            shipPosition: sph
                        })
                    }
                }
            }
        },



        //------------------------end of ship placement---------------------//

    },




    created: function () {
        this.getURL();

    },

});