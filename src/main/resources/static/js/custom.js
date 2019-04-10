function showGallery(id) {
    fetch("/events/all-pictures/" + id)
        .then((response) => response.json()
            .then((json) => {
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                console.log(json);
                json.forEach((image, index) => {
                    $('#content-container')
                        .append(`<img src="${image.url}" width="200px" height="200px">`);
                    console.log(image.url);
                });
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })

}


function showAttendingEvents() {
    fetch("/events/my-events/attending")
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('Attending events');
            $('#content-container').remove();
            $('.custom-container').append(`<div id="content-container" class="mt-4 text-center"></div>`);

            json.forEach((event, index) => {
                $('#content-container').append(
                    `<h4>Event: ${event.name}</h4> <h5>Date: ${event.startsOnDate.toString()} ${event.startsOnTime.toString()} </h2>
 <a href="/events/my-events/attending/${event.id}" class="btn btn-info">View</a>`);
            });
        })
        .catch(() => {
            $('#customText').text('Attending events');
        });

}


function showCreatedEvents() {
    fetch("/events/my-events/created")
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('Created events');
            $('#content-container').remove();
            $('.custom-container').append(`<div id="content-container" class="mt-4 text-center"></div>`);

            json.forEach((event, index) => {
                $('#content-container').append(
                    `<h4> Event: ${event.name}</h4><h5>Date: ${event.startsOnDate.toString()} ${event.startsOnTime.toString()} </h5>`);


                if (event.notOpenToRegister) {
                    $('#content-container').append(`<p>Open For Registration: No</p>`);
                } else
                    $('#content-container').append(`<p>Open For Registration: Yes</p>`);

                $('#content-container').append(`<a href="/events/my-events/created/${event.id}" class="btn btn-info">View</a>
<a href="/events/my-events/created/edit/${event.id}" class="btn btn-info">Edit</a>
<a href="/events/my-events/created/delete/${event.id}" class="btn btn-info">Delete</a><hr/>`);
            });
        })
        .catch(() => {
            $('#customText').text('Created events');
        });


}

//
function myFunction() {
    $('input').focus(function () {
        $(this).prev().addClass('stylee');
    }).blur(function () {
        if ($(this).val()) {
            $(this).prev().addClass('stylee');
        } else {
            $(this).prev().removeClass('stylee');
        }
    });

    $('select').focus(function () {
        $(this).prev().addClass('stylee');
    }).blur(function () {
        if ($(this).val()) {
            $(this).prev().addClass('stylee');
        } else {
            $(this).prev().removeClass('stylee');
        }
    });
}