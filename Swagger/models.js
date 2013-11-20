module.exports = {
    Send : {
        id : 'Send',
        required : [
            'message',
            'address'
        ],
        properties : {
            message : {
                type : 'string',
                description : 'The message'
            },
            address : {
                type : 'string',
                format : 'int32',
                description : 'The subscriber number who owns the access token'
            }
        }
    },
    Charge : {
        id : 'Charge',
        description : 'TODO',
        required : [
            'endUserId',
            'referenceCode',
            'amount'
        ],
        properties : {
            endUserId : {
                type : 'string',
                description : 'TODO'
            },
            referenceCode : {
                type : 'string',
                description : 'TODO'
            },
            amount : {
                type : 'string',
                description : 'TODO'
            },
            description : {
                type : 'string',
                description : 'TODO'
            }
        }
    }
};