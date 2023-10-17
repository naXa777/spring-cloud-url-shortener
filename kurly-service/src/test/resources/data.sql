-- test data
MERGE INTO link
    (id, creation_date, original_url, short_code)
    KEY(id)
VALUES (3, '2019-03-08 20:04:44.343000000', 'http://test.com', '1'),
       (4, '2019-03-09 04:20:44.343000000', 'https://google.com', '2');
