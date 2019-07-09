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

                <p class="card-header-title is-size-3">
                    Edit ticket
                </p>
            </div>
            <div class="card-content">
                <b-field :type="isCategoryNotNull" horizontal label="Category*" placeholder="Select a category">
                    <b-select placeholder="Select a category" v-model="ticket.category">
                        <option
                                v-for="category in categories"
                                :key="category.id"
                                :value="category">
                            {{ category.name }}
                        </option>
                    </b-select>
                </b-field>

                <b-field :type="isNameValid" horizontal label="Name*">
                    <b-input name="name" v-model="ticket.name">
                    </b-input>
                </b-field>

                <b-field horizontal label=" Description">
                    <b-input type="textarea" v-model="ticket.description"></b-input>
                </b-field>

                <b-field :type="isUrgencyNotNull" horizontal label="Urgency*">
                    <b-select placeholder="Select a urgency" v-model="ticket.urgency">
                        <option v-for="urgency in urgencies" :value="urgency" :key="urgency">
                            {{ urgency }}
                        </option>
                    </b-select>
                </b-field>

                <b-field horizontal label="Desired resolution date">
                    <b-datepicker v-model="date"
                                  placeholder="Click to select..."
                                  :min-date="minDate"
                    >
                    </b-datepicker>
                </b-field>
                <b-field horizontal label="Attachments">
                    <attachments
                            :set-attachments="setAttachments"
                            :attachments="attachments"
                            :ticket-id="this.id"
                            :editable="true">
                    </attachments>
                </b-field>
                <b-field horizontal label="Comment">
                    <b-input v-model="comment.text" type="textarea"></b-input>
                </b-field>

                <div class="level">
                    <div class="level-left"></div>
                    <div class="level-right">
                        <div class="level-item">
                            <b-button :disabled="!isFormValid"
                                      type="is-info"
                                      @click="saveTicket('DRAFT')">
                                Save as draft
                            </b-button>
                        </div>

                        <div class=" level-item">
                            <b-button :disabled="!isFormValid"
                                      type="is-primary"
                                      @click="saveTicket('NEW')">
                                Submit
                            </b-button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import dateFormat from 'dateformat';
    import Attachments from "../components/Attachments";

    export default {
        components: {Attachments},
        props: ['id'],
        data() {
            return {
                ticket: {
                    category: null,
                    name: '',
                    description: '',
                    urgency: null,
                    desiredDate: this.formattedDate,
                    state: '',
                },
                attachments: [],
                comment: {text: ''},
                editable: true,
                categories: [],
                urgencies: [],
                minDate: new Date(),
                date: new Date(),
                dateoptions: {year: 'numeric', month: 'numeric', day: 'numeric'},
            }
        },
        methods: {
            setAttachments(attachments) {
                this.attachments = attachments;
            },
            saveTicket(state) {
                this.ticket.state = state;
                this.ticket.comment = this.comment;
                this.ticket.attachments = this.attachments;

                this.$http
                    .put(this.$context + 'api/tickets/' + this.id, this.ticket)
                    .then(res => this.$router.push(res.headers.location))
            },
        },
        computed: {
            formattedDate() {
                this.ticket.desiredDate = dateFormat(this.date, "dd/mm/yyyy");
                return dateFormat(this.date, "dd/mm/yyyy");
            },
            isCategoryNotNull() {
                if (this.ticket.category === null) {
                    return "is-danger";
                } else {
                    return "is-success";
                }
            },
            isNameValid() {
                if (this.ticket.name === null || this.ticket.name === '') {
                    return "is-danger";
                } else {
                    return "is-success";
                }
            },
            isUrgencyNotNull() {
                if (this.ticket.urgency === null) {
                    return "is-danger";
                } else {
                    return "is-success";
                }
            },
            isFormValid() {
                return this.isCategoryNotNull === "is-success" &&
                    this.isNameValid === "is-success" &&
                    this.isUrgencyNotNull === "is-success";
            }
        },
        created() {
            this.$http.get(this.$context + 'api/categories')
                .then(res => {
                    this.categories = res.data
                });
            this.$http.get(this.$context + 'api/urgencies')
                .then(res => {
                    this.urgencies = res.data;
                });
            this.$http.get(this.$context + 'api/tickets/' + this.id)
                .then(res => {
                    this.ticket = res.data;
                });
        }
    }
</script>
