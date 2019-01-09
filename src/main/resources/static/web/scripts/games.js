fetch('http://localhost:8080/api/games')
    .then((res) => res.json())
    .then((json) => {
        data = json;
        console.log(data);
        listOfGames();
    })
    .catch((err) => {
        console.log(err);
        text.append(data.message);
    });


function listOfGames() {
    var gamesList = document.getElementById("games");
    for (var x = 0; x < data.length; x++) {
        var li = document.createElement("li");
        var gameData = new Date(data[x].created);
        li.append(gameData.toLocaleString() + " ");

        if (data[x].GamePlayers.length > 0) {
            for (var y = 0; y < data[x].GamePlayers.length; y++) {
                var player = data[x].GamePlayers[y].player.email
                if (data[x].GamePlayers.length < 2){
                    li.append(player + " waiting for opponent")
                }else{
                    li.append(player + " ");
                }
              
            }
         
            gamesList.append(li);
        }

    }
}