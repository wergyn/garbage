<template>
    <div class="container">
        <div class="level">
            <div class="level-left"></div>
            <div class="level-right">
                <div class="level-item">
                    <b-button tag="router-link"
                              to="tickets/new"
                              type="is-link"
                              size="is-medium"
                              v-if="user.role !== 'ENGINEER'">
                        Create new ticket
                    </b-button>
                </div>
            </div>
        </div>


        <b-tabs expanded>
            <b-tab-item label="All tickets">
                <ticket-table :type='this.ticketsTableType[0]'></ticket-table>
            </b-tab-item>
            <b-tab-item label="My tickets">
                <ticket-table :type='this.ticketsTableType[1]'></ticket-table>
            </b-tab-item>
        </b-tabs>
    </div>
</template>

<script>
    import TicketTable from "../components/TicketTable";

    export default {
        components: {TicketTable},
        props: ['user'],
        data() {
            return {
                ticketsTableType: ['all', 'my']
            }
        }
    }
</script>
