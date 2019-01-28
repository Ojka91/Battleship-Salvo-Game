var myApp = new Vue({
    el: '#app',
    data: {
        dataClass: "",
        dataGames: "",
        ourData: {
            name: "",
            pwd: "",
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
                    myApp.playerStatus = true;
                  
                    
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
                    myApp.playerStatus = false;
                          
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

    getDataForm(){
        this.ourData.name = document.getElementById("email").value;
        this.ourData.pwd = document.getElementById("password").value;
        this.getLogIn();
        
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