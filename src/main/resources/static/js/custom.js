function myFunction() {
    $(window, document, undefined).ready(function() {

        let $ripples = $('.ripples');

        $ripples.on('click.Ripples', function(e) {

            let $this = $(this);
            let $offset = $this.parent().offset();
            let $circle = $this.find('.ripplesCircle');

            let x = e.pageX - $offset.left;
            let y = e.pageY - $offset.top;

            $circle.css({
                top: y + 'px',
                left: x + 'px'
            });

            $this.addClass('is-active');

        });


        $ripples.on('animationend webkitAnimationEnd mozAnimationEnd oanimationend MSAnimationEnd', function(e) {
            $(this).removeClass('is-active');
        });

        // $(document).ready();

    });
//
    $('input').focus(function() {
        $(this).prev().addClass('stylee');
    }).blur(function() {
        if($(this).val())
        {
            $(this).prev().addClass('stylee');
        }
        else
        {
            $(this).prev().removeClass('stylee');
        }
    });

    $('select').focus(function() {
        $(this).prev().addClass('stylee');
    }).blur(function() {
        if($(this).val())
        {
            $(this).prev().addClass('stylee');
        }
        else
        {
            $(this).prev().removeClass('stylee');
        }
    });
}