var app1 = new Vue({
    el: '#loginForm',
    data: {
        email: '',
        password: '',
        organiser: false,
        alert: true
    },
    methods: {
        submit: function(event) {
            event.preventDefault();
            axios({
              method: 'post',
              url: 'login',
              params: {email: this.email,
                        password: this.password,
                        organiser: this.organiser}
            }).then(function (response) {
                if (response.status == 202) {
                    if (app1.organiser) window.open("organiser.jsp","_self")
                    else window.open("player.jsp", "_self")

                }

            }).catch(function (error) {
                  if (error.response) {
                    app1.alert = false;
                  }
                });
        }
    }})