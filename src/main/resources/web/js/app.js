d3.select('.container')
    .on('click', function () {
        d3.select(this)
            .append('div')
            .attr('class', 'activity')
            .text('Hello [ ' + moment().format('HH:mm:ss') + '- ' + moment().format('HH:mm:ss') + ' ]')
    })
