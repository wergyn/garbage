import Vue from 'vue';
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import router from 'router';
import axios from 'axios';
import App from 'App.vue';
import uploader from 'vue-simple-uploader'
import StarRating from 'vue-star-rating'

Vue.use(Buefy);
Vue.use(uploader);
Vue.component('star-rating', StarRating);
Vue.prototype.$uploader = uploader;
Vue.prototype.$http = axios;
Vue.prototype.$context = 'http://localhost:8080/help-desk/';

new Vue({
    el: "#app",
    router,
    render: app => app(App)
});
