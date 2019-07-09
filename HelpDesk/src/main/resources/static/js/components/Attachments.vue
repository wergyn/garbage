<template>
    <div class="container">
        <b-message v-if="this.errorShow" type="is-error" has-icon>
            {{errorMessage}}
        </b-message>
        <template v-for="item in attachmentsChild">
            <div style="padding-bottom: 15px">
                <span> File name: {{decode_utf8(item.name.substring(36))}}</span>

                <b-button
                        class="is-success"
                        tag="a"
                        :href="item.link">
                    Download
                </b-button>

                <b-button v-if="editable"
                          class="is-danger"
                          @click="removeFile(item)">
                    Remove
                </b-button>
            </div>
        </template>
        <template v-if="editable">
            <uploader :options="options"
                      class="uploader-example"
                      @file-added="fileAdded"
                      @file-success="fileSuccess">
                <uploader-unsupport></uploader-unsupport>
                <uploader-drop>
                    <uploader-btn allowDuplicateUploads :attrs="attrs">select files</uploader-btn>
                </uploader-drop>
                <uploader-list></uploader-list>
            </uploader>
        </template>
    </div>
</template>

<script>
    export default {
        props: ['attachments', 'ticketId', 'setAttachments', 'editable'],
        data() {
            return {
                id: this.ticketId,
                editable: this.editable,
                maxSize: 5242880,
                errorMessage: '',
                errorShow: false,
                options: {
                    target: this.$context + 'api/attachments',
                    chunkSize: 5242880,
                    testChunks: false
                },
                attrs: {
                    accept: "image/jpeg, image/png, image/jpg, "
                        + "application/vnd.openxmlformats-officedocument.wordprocessingml.document, "
                        + "application/msword, application/pdf"
                },
                attachmentsChild: this.attachments
            }
        },
        created() {
            if (this.id !== undefined) {
                this.$http.get(this.$context + 'api/tickets/' + this.id + '/attachments')
                    .then(res => {
                        this.attachmentsChild = res.data;
                        this.setAttachments(this.attachmentsChild);
                    });
            }
        },
        methods: {
            decode_utf8(s) {
                return decodeURIComponent(escape(s));
            },
            fileSuccess(rootFile, file, message, chunk) {
                this.attachmentsChild.push(JSON.parse(message));
                this.setAttachments(this.attachmentsChild);
                file.cancel();
            },
            fileAdded(file, event) {
                this.errorShow = false;
                file.pause();
                if (this.attrs.accept.indexOf(file.fileType) < 0) {
                    this.errorMessage = 'The selected file type is not allowed. ' +
                        'Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.';
                    this.errorShow = true;
                    file.cancel();
                } else if (file.size > this.maxSize) {
                    this.errorMessage = 'The size of attached file should not be greater than 5 Mb. ' +
                        'Please select another file.';
                    this.errorShow = true;
                    file.cancel();
                } else {
                    file.resume();
                }
            },
            removeFile(item) {
                this.$http.delete(this.$context + 'api/attachments/' + item.name);
                this.attachmentsChild = this.attachmentsChild.filter(el => el.name !== item.name)
                this.setAttachments(this.attachmentsChild);
            }
        }
    }
</script>

<style scoped>

</style>