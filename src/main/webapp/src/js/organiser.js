Vue.component('active-games-display', {
    props: ['game', 'invite', 'terminate'],
    template: '<div class="container">' +
        '       <div class="container">' +
        '           <div class="row">' +
        '               <div class="col"><div>{{ game.title }}</div>' +
        '                   <div>{{ game.type }}</div>' +
        '                   <div>{{ game.termination  }}</div>' +
        '               </div>' +
        '               <div class="col">' +
        '                   <input type="button" class="btn btn-secondary float-right btn-space" :value="terminate">' +
        '                   <input type="button" class="btn btn-primary float-right btn-space" :value="invite">' +
        '               </div>' +
        '            </div>' +
        '       </div>' +
        '      </div>'
});

Vue.component('terminated-games-display', {
    props: ['game', 'del', 'game-id'],
    template: '<div class="container">' +
        '       <div class="container">' +
        '           <div class="row">' +
        '               <div class="col"><div>{{ game.title }}</div>' +
        '                   <div>{{ game.type }}</div>' +
        '                   <div>{{ game.termination  }}</div>' +
        '               </div>' +
        '               <div class="col">' +
        '                   <input type="button" class="btn btn-secondary float-right btn-space" v-on:click="remove(gameId)" :value="del">' +
        '               </div>' +
        '            </div>' +
        '       </div>' +
        '      </div>',
    methods: {
        remove: function (gameId) {
            axios({
                method: 'post',
                url: 'games/' + gameId + '/delete'
            });
            terminatedGames.listOfGames.forEach(function (value, index) { // remove from the list without reloading page
                if (value.gameId == gameId) terminatedGames.listOfGames.splice(index, 1);
            });
        }
    }
});

Vue.component('stats-display', {
    props: [''],
    template: ''
});

var activeGames = new Vue({
    el: "#active",
    data: {
        listOfGames: [{title:"myCoolGame", type:"Matrix", termination:0, gameId: 1},
            {title:"myRatherAmusingGame", type:"Binär", termination:0, gameId: 2},
            {title:"myUnderwhelmingGame", type:"Matrix", termination:0, gameId: 3},
            {title:"myDisappointingGame", type:"Matrix", termination:0, gameId: 4}]
    }
});

var terminatedGames = new Vue({
    el: "#terminated",
    data: {
        listOfGames: [{title:"myOldGame", type:"Matrix", termination:0, gameId: 1}]
    }
});

var stats = new Vue({
    el: "#stats"
});