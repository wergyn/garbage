import Vue from 'vue';
import VueRouter from 'vue-router';
import TicketsList from 'pages/TicketList.vue'
import NewTicket from 'pages/NewTicket.vue'
import TicketOverview from 'pages/TicketOverview.vue'
import EditTicket from 'pages/EditTicket'

Vue.use(VueRouter);

const routes = [
    {path: "/help-desk", component: TicketsList, props: true},
    {path: "/help-desk/tickets", component: TicketsList, props: true},
    {path: "/help-desk/tickets/new", component: NewTicket, props: true},
    {path: "/help-desk/tickets/:id", component: TicketOverview, props: true},
    {path: "/help-desk/tickets/:id/edit", component: EditTicket, props: true}
];

export default new VueRouter({
    mode: 'history',
    routes
})