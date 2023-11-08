-- user roles
insert into roles(id, role)
VALUES (1, 'ADMIN'),
       (2, 'USER');


-- admin user
insert into users(id, email, first_name, last_name, password)
VALUES (1, 'admin@admin.com', 'Admin', 'Adminov',
        'b6556f0100f917a441fa338792514eb78796f20f57185b530ddcce445bdca2d742c8ffb88d1f9b0323ad6a7823e6ae08');

insert into users_roles(user_entity_id, roles_id)
VALUES (1, 1),
       (1, 2);


-- bike manufacturers
insert into manufacturers(id, name)
VALUES (1, 'Stanton'),
       (2, 'EVIL'),
       (3, 'BMC'),
       (4, 'Falkenjagd'),
       (5, 'Surly');


-- bike models
insert into models(id, category, name, manufacturer_id)
VALUES (1, 'MTB', 'Switchback', 1),
       (2, 'MTB', 'Switch9er', 1),
       (3, 'MTB', 'Slackline', 1),
       (4, 'MTB', 'Sherpa', 1),
       (5, 'MTB', 'SwitchbackTi', 1),
       (6, 'MTB', 'Switch9erTi', 1),
       (7, 'MTB', 'SlacklineTi', 1),
       (8, 'MTB', 'SherpaTi', 1);

insert into models(id, category, name, manufacturer_id)
VALUES (9, 'MTB', 'Wreckoning', 2),
       (10, 'MTB', 'Offering', 2),
       (11, 'MTB', 'Following', 2),
       (12, 'MTB', 'Insurgent', 2);

insert into models(id, category, name, manufacturer_id)
VALUES (13, 'MTB', 'Fourstroke', 3),
       (14, 'MTB', 'Twostroke', 3);

insert into models(id, category, name, manufacturer_id)
VALUES (15, 'TOURING', 'Hoplit Pi Plus', 4),
       (16, 'ROAD', 'Aristos R', 4);

insert into models(id, category, name, manufacturer_id)
VALUES (17, 'MTB', 'Karate Monkey', 5);


-- bikes
insert into bikes(id, description, frame_material, frame_size, price, wheel_size, model_id)
VALUES (1,
        'The Stanton Sherpa is a 29er that retains the grin-inducing playfulness of a smaller-wheeled machine thanks to our uncompromising commitment to Positive Engineering. When new standards emerge we look for innovative engineering solutions that stay true to our principles, we don’t compromise our geometry just to make things fit.',
        'STEEL', 'M', 2500, 29, 4),
    (2, 'The Sherpa Ti is our bench mark trail/XC-specific frame, designed to be run with 29” or 27.5″+ wheels.',
     'TITANIUM', 'M', 4500, 29 ,8),
    (3, 'The Slackline is our all day trail slayer – the rider’s ride. The choice of customers who’ve been there, ridden that, and know what they want.',
     'STEEL', 'XL', 2000, 27.5, 3),
    (4, 'The choice of customers who’ve been there, ridden that, and know what they want.',
     'TITANIUM', 'M', 4000, 27.5, 7),
    (5, 'Yeah the geometry’s progressive (isn’t everyone’s these days?) but it’s your riding that’s going to see the real progression. Whatever the Switch9er comes up against it won’t take no for answer, it’s just the way it’s built.',
     'STEEL', 'S', 2100, 29, 2),
    (6, 'It all comes together in the Switch9er Ti – the ultimate enduro hardtail. The daddy of our playful Switch hardtail range, it’s a bike that does things it’s not supposed to, so you can do things you didn’t know you could.',
     'TITANIUM', 'M', 4800, 29, 6),
    (7, 'We were born to play, and so was the Switchback Gen 3 – the most playful hardtail on the market.',
     'STEEL', 'M', 2500, 27.5, 1),
    (8, 'The original aim of the Switchback was to design the most playful natured and confidence inspiring aggressive frame that we possibly could. The Switchback Ti is an evolution of this ethos.',
     'TITANIUM', 'M', 4800, 27.5, 5);

-- pictures
insert into pictures(id, url, bike_id)
VALUES (1, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477482/BikeShop/Bikes/gucc3xzdky8oxaow9nv8.jpg', 1),
    (2, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477633/BikeShop/Bikes/syjv9vq3guvevhbu05ot.jpg', 2),
    (3, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477663/BikeShop/Bikes/zhrtojxjufufptvofd2v.jpg', 3),
    (4, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477714/BikeShop/Bikes/avtkkclcmekwztvval0m.jpg', 4),
    (5, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477742/BikeShop/Bikes/jcpttwbzmhnm35nnffwx.jpg', 5),
    (6, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477778/BikeShop/Bikes/tlwahm8soh6o8fruosiv.jpg', 6),
    (7, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477804/BikeShop/Bikes/kgxlhu4hewvollooqkc9.jpg', 7),
    (8, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477827/BikeShop/Bikes/ndgt2dwbi2sibknftsds.jpg', 8);
