const express = require('express');
const app = express();
require('dotenv').config();

const yelp = require('yelp-fusion');

const client = yelp.client();

// call auto complete
app.get('/autocomplete', (req, res) => {
    client.autocomplete({ text: req.query.keyword })
        .then((response) => {
            res.json(response.jsonBody.terms)
        })
        .catch((error) => {
            res.json(error)
        });
})


// call get businesses
app.get('/businesses', (req, res) => {
    let searchRequest = {
        term: req.query.keyword,
        location: req.query.location,
        radius: parseInt(req.query.distance),
        categories: req.query.category
    };

    console.log(searchRequest);

    client.search(searchRequest)
        .then((response) => {
            res.json(response.jsonBody);
        })
        .catch((error) => {
            res.json(error);
        });
})
// call get getBusinesses by current location
app.get('/businessesByCoords', (req, res) => {
    let searchRequest = {
        term: req.query.keyword,
        radius: parseInt(req.query.distance),
        categories: req.query.category,
        latitude: req.query.latitude,
        longitude: req.query.longitude
    };

    console.log(searchRequest);

    client.search(searchRequest)
        .then((response) => {
            res.json(response.jsonBody);
        })
        .catch((error) => {
            res.json(error);
        });
})

// get business by id
app.get('/business', (req, res) => {
    id = req.query.id;
    client.business(id).then((response) => {
        res.json(response.jsonBody);
    })
    .catch((error) => {
        res.json(error);
    });
})
// get business reviews
app.get('/reviews', (req, res) => {
    id = req.query.id;
    client.reviews(id).then((response) => {
        res.json(response.jsonBody);
    })
    .catch((error) => {
        res.json(error);
    });
})


app.listen(3000, (req, res) => {
    console.log("Server is up and running on port 3000");
})
