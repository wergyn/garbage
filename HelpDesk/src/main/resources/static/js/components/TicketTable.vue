<template>
    <div class="card">
        <header class="card-header">
            <div class="card-header-title">
                <b-field>
                    <b-input placeholder="Search..."
                             type="search"
                             icon="magnify"
                             v-model="search">
                    </b-input>
                </b-field>
            </div>
        </header>
        <div class="card-content">
            <b-table
                    :data="tableData"
                    :paginated=true
                    :pagination-simple=false
                    :per-page="5"
                    :loading="loading"
                    :default-sort="['urgency', 'desc']"
                    :search="search">

                <template slot-scope="props">
                    <b-table-column field="id" label="ID" widht="10px" sortable numeric>
                        {{ props.row.id }}
                    </b-table-column>
                    <b-table-column field="name" label="Name" sortable>
                        <router-link :to="'/help-desk/tickets/'+props.row.id+'/'">
                            {{ props.row.name }}
                        </router-link>
                    </b-table-column>
                    <b-table-column field="desiredDate" label="Desired date" sortable>
                        {{ new Date(props.row.desiredDate).toLocaleDateString() }}
                    </b-table-column>
                    <b-table-column field="urgency" label="Urgency" sortable :custom-sort="urgencySort">
                        {{ props.row.urgency }}
                    </b-table-column>
                    <b-table-column field="state" label="State" sortable>
                        {{ props.row.state }}
                    </b-table-column>
                    <b-table-column field="action" label="Action">
                        <b-dropdown v-if="props.row.actions.length > 0" hoverable aria-role="list">
                            <button class="button is-info" slot="trigger">
                                <span>Select action</span>
                                <b-icon icon="menu-down"></b-icon>
                            </button>

                            <template v-for="action in props.row.actions">
                                <b-dropdown-item aria-role="listitem"
                                                 @click="changeTicketState(action, props.row.id)">
                                    {{action}}
                                </b-dropdown-item>
                            </template>
                        </b-dropdown>
                    </b-table-column>
                </template>
            </b-table>
        </div>
    </div>
</template>

<script>
    export default {
        props: ['type'],
        data() {
            return {
                search: '',
                tickets: [],
                loading: false,
            }
        },
        computed: {
            tableData() {
                const keyword = this.search.toLowerCase()

                return this.search
                    ? this.tickets.filter(item => item.id.toString().includes(keyword)
                        || item.name.toLowerCase().includes(keyword)
                        || item.desiredDate.toString().includes(keyword)
                        || item.urgency.toLowerCase().includes(keyword)
                        || item.state.toLowerCase().includes(keyword))
                    : this.tickets
            }
        },
        methods: {
            urgencySort(a, b, isAsc) {
                const statuses = ['CRITICAL', 'HIGH', 'AVERAGE', 'LOW'];
                if (isAsc) {
                    return statuses.indexOf(b.urgency) - statuses.indexOf(a.urgency);
                } else {
                    return statuses.indexOf(a.urgency) - statuses.indexOf(b.urgency);
                }
            },
            getTickets() {
                let resource = this.$context + 'api/tickets/';
                if (this.type === 'my') {
                    resource += this.type;
                }

                this.loading = true;
                this.$http
                    .get(resource)
                    .then(response => {
                        this.tickets = response.data;
                        this.loading = false;
                    });
            },
            changeTicketState(action, ticketId) {
                this.$http
                    .post(this.$context + 'api/tickets/' + ticketId + '/actions',
                        {action: action})
                    .then(response => {
                        this.getTickets(this.type)
                    })
            }
        },
        created() {
            this.getTickets()
        },
    }
</script>

<style scoped>

</style>