var myApp = new Vue({
    el: '#app',
    data: {
        numbers: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        letters: ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"],
        data: "",
    },

    methods: {
        // getData: fetch('http://localhost:8080/api/game_view/1')
        // .then((res) => res.json())
        // .then((json) => {
        //     this.data = json;
        //     console.log(this.data);
        // })
        // .catch((err) => {
        //     console.log(err);
        //     text.append(data.message);
            
        // }),

    },

    created: function () {

    },

});

