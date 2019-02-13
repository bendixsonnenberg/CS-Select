Vue.component('achievement',{
    props: ['achievement'],

    template:
        '<div class="card">\n' +
        '  <div class="card-body" v-bind:class="{ \'bg-success\': achievement.state > 2}">\n' +
        '    <h5 class="card-title"  v-if="achievement.state > 0">{{ achievement.name }}</h5>\n' +
        '    <h5 class="card-title"  :title="localisation.unknownAchievementTooltip" ' +
        '      v-else>{{localisation.unknownAchievement}}</h5>\n' +
        '    <div  v-if="achievement.state > 1"class="card-text">\n' +
        '      {{ achievement.desc }}\n' +
        '    </div>' +
        '    <div class="card-text" v-if="achievement.state > 2"> {{localisation.completed}}</div>' +
        '  </div>\n' +
        '</div>'

})

/*  State   Description
    0       invisible
    1       hidden (the box is there, but nothing else)
    2       shown
    3       completed
 */

var achievements = new Vue({
    el: '#achievements',
    data: {
        achievementList: []

    },
    mounted: function () {
        axios('users/achievements').then(function (response) {
            achievements.achievementList = response.data;
        })
    }
})