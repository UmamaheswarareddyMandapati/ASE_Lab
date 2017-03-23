/**
 * Created by Ramgopal on 26-10-2016.
 */
var express = require('express');
var app = express();
var request = require('request');
app.get('/gettags', function (req, res) {
    var result={
        'tags': []
    };

    request('https://api.clarifai.com/v1/tag/?url=http://cei.umkc.edu/images/KCSkyline.jpg&access_token=pu285UxYIDAwdYdFJQGaDOhGgBNM0C', function (error, response, body) {
        //Check for error
        if(error){
            return console.log('Error:', error);
        }

        //Check for right status code
        if(response.statusCode !== 200){
            return console.log('Invalid Status Code Returned:', response.statusCode);
        }
        //All is good. Print the body
        body = JSON.parse(body);
        var ven = body.results;

        console.log(ven[0].result.tag.classes);
        for(var i=0;i<ven[0].result.tag.classes.length;i++)
        {
            result.tags.push({'tag' :ven[0].result.tag.classes[i]});

        }
        res.contentType('application/json');
        res.write(JSON.stringify(result));
        res.end();
    });
    console.log(result);


})

var server = app.listen(8081, function () {
    var host = server.address().address
    var port = server.address().port

    console.log("Example app listening at http://127.0.0.1:8081", host, port)
})