<template>
    <b-table
            :data="histories"
            :paginated=true
            :pagination-simple=false
            :per-page="5"
            :loading="loading"
            :default-sort="['date', 'desc']">

        <template slot-scope="props">
            <b-table-column field="date" label="Date" sortable>
                {{ props.row.date }}
            </b-table-column>
            <b-table-column field="user" label="User" sortable>
                {{ props.row.user.firstName }} {{ props.row.user.lastName }}
            </b-table-column>
            <b-table-column field="action" label="Action" sortable>
                {{ props.row.action }}
            </b-table-column>
            <b-table-column field="description" label="description" sortable>
                {{ props.row.description }}
            </b-table-column>
        </template>
    </b-table>
</template>

<script>
    export default {
        props: ['id'],
        data() {
            return {
                histories: [],
                loading: false,
            }
        },
        methods: {
            getHistories() {
                this.loading = true;
                this.$http
                    .get(this.$context + 'api/tickets/' + this.id + '/histories')
                    .then(response => {
                        this.histories = response.data;
                        this.loading = false;
                    });
            }
        },
        created() {
            this.getHistories();
        },
    }
</script>

<style scoped>

</style>