var myApp = new Vue({
    el: '#app',
    data: {
        data: "",
        json: {
            "players": [{

            }]

        },
    },


    methods: {
        getData: function () {

            fetch('/api/leaderboard/')
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
            this.getPlayers();

        },

        getPlayers: function () {


        }


    },
    computed: {
        sortedData() {
            let sorted = {};
            Object
                .keys(this.data).sort((a, b) => {
                    return this.data[b].Total - this.data[a].Total;
                })
                .forEach(function (key) {
                    sorted[key] = myApp.data[key];
                });
            return sorted;
        }
    },


    created: function () {
        this.getData();
    },

});