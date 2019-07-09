<template>
    <div class="container">
        <b-table
                :data="comments"
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
                <b-table-column field="text" label="Text" sortable>
                    {{ props.row.text }}
                </b-table-column>
            </template>
        </b-table>
        <b-field horizontal label="Comment">
            <b-input v-model="comment.text" type="textarea"></b-input>
            <b-button type="is-info"
                      @click="addComment">
                Add comment
            </b-button>
        </b-field>
    </div>
</template>

<script>
    export default {
        props: ['id'],
        data() {
            return {
                comments: [],
                comment: {text: ''},
                loading: false,
            }
        },
        methods: {
            addComment() {
                this.$http
                    .post(this.$context + 'api/tickets/' + this.id + '/comments', this.comment)
                    .then(this.getComments())
            },
            getComments() {
                this.loading = true;
                this.$http
                    .get(this.$context + 'api/tickets/' + this.id + '/comments')
                    .then(response => {
                        this.comments = response.data;
                        this.loading = false;
                    });
            }
        },
        created() {
            this.getComments();
        },
    }
</script>

<style scoped>

</style>