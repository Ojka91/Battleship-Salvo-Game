var myApp = new Vue({
    el: '#app',
    data: {
        data: "",
        json:{
           
        },
    },

    methods: {
        getData: function () {

            fetch('/api/games/')
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

        getPlayers: function(){
                     
            

        }


    },
    created: function () {
        this.getData();
    },

});