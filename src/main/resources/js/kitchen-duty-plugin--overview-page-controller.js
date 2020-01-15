AJS.toInit(function () {
    AJS.log('KDP: Overview Page Controller initializing ...');
    const baseUrl = AJS.params.baseURL;
    window.KDPrestUrl = baseUrl + '/rest/kitchenduty/1.0';

    // Init Base SOY template
    const overviewPageTemplate = JIRA.Templates.KDO.overviewPage();
    AJS.$('#kdp-overview-page-container').html(overviewPageTemplate);

    const calendarEl = AJS.$('#kdp-calendar')[0];
    const calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: ['interaction', 'dayGrid'],
        weekNumbers: true,
        height: 700,
        fixedWeekCount: false,
        events: function (info, successCallback, failureCallback) {
            let year = moment(info.start).add('days', 10).format('YYYY');
            let month = moment(info.start).add('days', 10).format('M');
            AJS.$.ajax({
                url: window.KDPrestUrl + '/overview_page/year/' + year + '/month/' + month,
                dataType: 'json',
                success: function (rawEvents) {
                    let events = [];
                    AJS.$(rawEvents).each(function () {
                        let users = AJS.$(this).attr('users');
                        events.push({
                            title: users.join(', '),
                            start: AJS.$(this).attr('start'),
                            end: AJS.$(this).attr('end'),
                            color: users.length > 0 ? '#36B37E' : '#FFAB00'
                        });
                    });
                    successCallback(events);
                }
            });
        }
    });

    calendar.render();
});
