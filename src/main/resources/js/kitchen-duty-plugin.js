function showSuccessFlag(message) {
    AJS.flag({
        type: 'success',
        title: 'Kitchen Duty Plugin',
        close: 'auto',
        body: message
    });
}

function showErrorFlag(message) {
    AJS.flag({
        type: 'error',
        title: 'Kitchen Duty Plugin',
        close: 'auto',
        body: message
    });
}
