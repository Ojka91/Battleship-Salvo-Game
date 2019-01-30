var myApp = new Vue({
    el: '#app',
    data: {
        dataClass: "",
        dataGames: "",
        ourData: {
            playerEmail: "",
            password: "",
        },
        playerStatus: false,


    },


    methods: {
        getDataClass: function () {

            fetch('/api/leaderboard/')
                .then((res) => res.json())
                .then((json) => {
                    this.dataClass = json;
                    console.log(this.dataClass);


                })
                .catch((err) => {
                    console.log(err);
                    //  text.append(data.message);
                    console.log("errordimierda");
                })
        },

        getLogIn: function () {
            fetch("/api/login", {
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    method: 'POST',
                    body: this.getBody(this.ourData)
                })
                .then(function (data) {
                    console.log('Request success: ', data);
                    if (data.status == 200) {
                        myApp.getDataGames();

                    } else {
                        alert("error login in")
                    }



                })
                .catch(function (error) {
                    console.log('Request failure: ', error);
                });
        },

        logOut: function () {
            fetch("/api/logout", {

                    method: 'POST',
                })
                .then(function (data) {
                    console.log('Request success: ', data);
                    if (data.status == 200) {
                        myApp.playerStatus = false;
                        myApp.ourData.playerEmail = "";
                        myApp.ourData.password = "";
                    } else {
                        alert("Error login in")
                    }


                })
                .catch(function (error) {
                    console.log('Request failure: ', error);
                });
        },



        getDataGames: function () {

            fetch('/api/games/')
                .then((res) => res.json())
                .then((json) => {
                    this.dataGames = json;
                    console.log(this.dataGames);
                    if (this.dataGames.username != null) {
                        console.log("deberia canvair a true")
                        this.playerStatus = true;
                        this.ourData.playerEmail = this.dataGames.username.email;
                        this.ourData.password = this.dataGames.username.password;
                    } else {
                        this.playerStatus = false;
                    }

                })
                .catch((err) => {
                    console.log(err);
                    //text.append(data.message);
                    console.log("errordimierda");
                })
        },

        getBody(json) {
            var body = [];
            for (var key in json) {
                var encKey = encodeURIComponent(key);
                var encVal = encodeURIComponent(json[key]);
                body.push(encKey + "=" + encVal);
            }
            return body.join("&");
        },



        formatDate(date) {

            date = new Date
            var monthNames = [
                "January", "February", "March",
                "April", "May", "June", "July",
                "August", "September", "October",
                "November", "December"
            ];

            var day = date.getDate();
            var monthIndex = date.getMonth();
            var year = date.getFullYear();

            return day + ' ' + monthNames[monthIndex] + ' ' + year;
        },

        getDataForm() {
            this.ourData.playerEmail = document.getElementById("email").value;
            this.ourData.password = document.getElementById("password").value;
            this.getLogIn();

        },

        signUp() {
            fetch('/api/players/', {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(myApp.ourData)
            }).then(function (response) {
                return response.json();
            }).then(function (json) {
                console.log('parsed json', json)
                myApp.getDataForm();
            }).catch(function (ex) {
                console.log('parsing failed', ex)
                alert("error signing up"+ ex);

            });
        },

        fillData() {
            this.ourData.playerEmail = document.getElementById("email").value;
            this.ourData.password = document.getElementById("password").value;
            this.signUp();
        },

    },
    computed: {
        sortedData() {
            let sorted = {};
            Object
                .keys(this.dataClass).sort((a, b) => {
                    return this.dataClass[b].Total - this.dataClass[a].Total;
                })
                .forEach(function (key) {
                    sorted[key] = myApp.dataClass[key];
                });
            return sorted;
        }
    },


    created: function () {
        this.getDataClass();
        this.getDataGames();

    },

});