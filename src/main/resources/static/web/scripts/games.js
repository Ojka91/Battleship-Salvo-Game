var myApp = new Vue({
    el: '#app',
    data: {
        dataClass: "",
        dataGames: "",
     
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
        }


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