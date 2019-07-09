<template>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <div class="card-header-title">
                    <b-button tag="router-link"
                              to="/help-desk/tickets"
                              type="is-link"
                              size="is-medium">
                        Ticket List
                    </b-button>
                </div>
                <div class="card-header-title">
                    <span>Ticket ({{ticket.id}}) - {{ticket.name}}</span>
                </div>
                <div class="is-right" style="padding-right: 10px;padding-top: 10px">
                    <b-button tag="router-link"
                              to="edit"
                              type="is-link"
                              size="is-medium"
                              v-if="this.user.email === ticket.owner.email && ticket.state === 'DRAFT'">
                        Edit
                    </b-button>
                </div>
            </div>
            <div class="card-content">
                <b-field class="is-pulled-right" v-if="ticket.state === 'DONE'">
                    <feedback :id="this.id"
                              :user="this.user"
                              :ticketOwner="ticket.owner">
                    </feedback>
                </b-field>
                <b-field horizontal label="Created On:">
                    <span>{{new Date(ticket.createdOn).toLocaleDateString()}}</span>
                </b-field>
                <b-field horizontal label="Status:">
                    <span>{{ticket.state}}</span>
                    <b-field horizontal label="Category:">
                        <span>{{ticket.category.name}}</span>
                    </b-field>
                </b-field>
                <b-field horizontal label="Urgency:">
                    <span>{{ticket.urgency}}</span>
                </b-field>
                <b-field horizontal label="Desired resolution date:">
                    <span v-if="ticket.desiredDate === null">n/a</span>
                    <span v-else>{{new Date(ticket.desiredDate).toLocaleDateString()}}</span>
                </b-field>
                <b-field horizontal label="Owner:">
                    <span>{{ticket.owner.firstName}} {{ticket.owner.lastName}}</span>
                </b-field>
                <b-field horizontal label="Approver:">
                    <span v-if="ticket.approver === null">n/a</span>
                    <span v-else>{{ticket.approver.firstName}} {{ticket.approver.lastName}}</span>
                </b-field>
                <b-field horizontal label="Assignee:">
                    <span v-if="ticket.assignee === null">n/a</span>
                    <span v-else>{{ticket.assignee.firstName}} {{ticket.assignee.lastName}}</span>
                </b-field>
                <b-field horizontal label="Description:">
                    <span>{{ticket.description}}</span>
                </b-field>
                <b-field horizontal label="Attachments:">
                    <attachments :ticketId="this.id" :editable="editable"></attachments>
                </b-field>
                <b-tabs expanded>
                    <b-tab-item label="History">
                        <history-table :id="this.id"></history-table>
                    </b-tab-item>
                    <b-tab-item label="Comments">
                        <comment-table :id="this.id"></comment-table>
                    </b-tab-item>
                </b-tabs>
            </div>
        </div>
    </div>
</template>

<script>
    import HistoryTable from "../components/HistoryTable";
    import CommentTable from "../components/CommentTable";
    import Attachments from "../components/Attachments";
    import Feedback from "../components/Feedback";

    export default {
        components: {Feedback, Attachments, HistoryTable, CommentTable},
        props: ['id', 'user'],
        data() {
            return {
                ticket: null,
                editable: false
            }
        },
        created() {
            this.$http.get(this.$context + 'api/tickets/' + this.id)
                .then(res => {
                    this.ticket = res.data;
                });
        }
    }
</script>

<style scoped>

</style>