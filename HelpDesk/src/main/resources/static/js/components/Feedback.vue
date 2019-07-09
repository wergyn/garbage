<template>
    <div class="container">
        <button v-if="this.user.email === this.ticketOwner.email && feedback === null"
                class="button is-primary is-medium"
                @click="isComponentModalActive = true">
            Leave Feedback
        </button>

        <button v-if="feedback !== null"
                class="button is-primary is-medium"
                @click="isComponentModalActive = true">
            Feedback
        </button>

        <b-modal :active.sync="isComponentModalActive" :width="640" scroll="keep">
            <div class="card">
                <div class="card-content">
                    <b-field>
                        <star-rating v-model="rate"
                                     :read-only="readonly"
                                     :star-size="90">
                        </star-rating>
                    </b-field>
                    <b-field label="Comment">
                        <b-input v-model="text"
                                 type="textarea"
                                 v-bind:readonly="readonly">
                        </b-input>
                    </b-field>

                    <div class="level">
                        <div class="level-left"></div>
                        <div class="level-right">
                            <div class="level-item">
                                <b-button type="is-danger"
                                          @click="closeFeedback">
                                    Close
                                </b-button>
                            </div>
                            <div class=" level-item">
                                <b-button v-if="readonly === false"
                                          type="is-primary"
                                          @click="saveFeedback">
                                    Save
                                </b-button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </b-modal>
    </div>
</template>

<script>
    export default {
        props: ['id', 'user', 'ticketOwner'],

        data() {
            return {
                isComponentModalActive: false,
                readonly: false,
                feedback: null,
                rate: 3,
                text: ''
            }
        },
        methods: {
            closeFeedback() {
                this.isComponentModalActive = false;
            },
            getFeedback() {
                this.$http.get(this.$context + 'api/tickets/' + this.id + '/feedback')
                    .then(res => {
                        this.feedback = res.data;
                        this.readonly = true;
                        this.text = this.feedback.text;
                        this.rate = this.feedback.rate;
                    })
            },
            saveFeedback() {
                this.$http.post(this.$context + 'api/tickets/' + this.id + '/feedback', {
                    text: this.text,
                    rate: this.rate
                })
                    .then(res => {
                        this.getFeedback();
                    });
            },
        },
        created() {
            this.getFeedback();
        },
    }
</script>

<style scoped>

</style>