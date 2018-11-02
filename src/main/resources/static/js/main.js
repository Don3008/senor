
function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var messageApi = Vue.resource('/message{/id}');

Vue.component('message-form', {
props: ['messages', 'messageAttr'],
    data: function() {
        return {
            coordinates: '',
            temperature: '',
            id: ''

        }
    },
    watch: {
        messageAttr: function(newVal, oldVal) {
            this.coordinates = newVal.coordinates;
            this.temperature = newVal.temperature;
            this.id = newVal.id;
            }
    },
    template:
        '<div>' +
            '<input type="coordinates" placeholder="Write coordinates" v-model="coordinates" />' +
            '<input type="temperature" placeholder="Write temperature" v-model="temperature" />' +
            '<input type="button" value="Save" @click="save" />' +
        '</div>',
        methods: {
            save: function() {
                var message = { coordinates: this.coordinates, temperature: this.temperature };

                if (this.id) {
                    messageApi.update({id: this.id}, message).then(result =>
                        result.json().then(data => {
                            var index = getIndex(this.messages, data.id);
                            this.messages.splice(index, 1, data);
                            this.coordinates = ''
                            this.temperature = ''
                            this.id = ''
                        })
                    )
                } else {
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);
                        this.coordinates = ''
                        this.temperature = ''
                })
                )
                }
            }
        }
});

Vue.component('message-row', {
    props: ['message', 'editMethod', 'messages'],
    template: '<div>' +
        '<i>({{ message.id }})</i> {{ message.coordinates }} {{ message.temperature }}' +
        '<span style="position: absolute; right: 0">' +
            '<input type="button" value="Edit" @click="edit" />' +
            '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
        methods: {
            edit: function(message) {
                this.editMethod(this.message);
            },
            del: function() {
                messageApi.remove({id: this.message.id}).then(result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(this.message), 1)
                    }
                })
            }
        }
});

Vue.component('messages-list', {
    props: ['messages'],
    data: function() {
        return {
            message: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
            '<message-form :messages="messages" :messageAttr="message"/>' +
            '<message-row v-for="message in messages" :key="message.id" :message="message" ' +
            ':editMethod="editMethod" :messages="messages" />'+
        '</div>',
  created: function() {
    messageApi.get().then(result =>
        result.json().then(data =>
            data.forEach(message => this.messages.push(message))
        )
    )
  },
  methods: {
    editMethod: function(message) {
        this.message = message;
    }
  }
});

var app = new Vue({
  el: '#app',
  template: '<messages-list :messages="messages" />',
  data: {
    messages: []
  }
});