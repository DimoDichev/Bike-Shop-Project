-- user roles
insert into roles(id, role)
VALUES (1, 'ADMIN'),
       (2, 'MODERATOR'),
       (3, 'USER');


-- admin user
insert into users(id, email, first_name, last_name, password, enabled)
VALUES (1, 'dzdichev@gmail.com', 'Admin', 'Adminov',
        'b6556f0100f917a441fa338792514eb78796f20f57185b530ddcce445bdca2d742c8ffb88d1f9b0323ad6a7823e6ae08', true);

insert into users_roles(user_entity_id, roles_id)
VALUES (1, 1),
       (1, 2),
       (1, 3);


-- bike manufacturers
insert into manufacturers(id, name)
VALUES (1, 'Stanton'),
       (2, 'EVIL'),
       (3, 'BMC'),
       (4, 'Falkenjagd'),
       (5, 'Surly');


-- bike models
insert into models(id, name, manufacturer_id)
VALUES (1, 'Switchback', 1),
       (2, 'Switch9er', 1),
       (3, 'Slackline', 1),
       (4, 'Sherpa', 1),
       (5, 'SwitchbackTi', 1),
       (6, 'Switch9erTi', 1),
       (7, 'SlacklineTi', 1),
       (8, 'SherpaTi', 1);

insert into models(id, name, manufacturer_id)
VALUES (9, 'Wreckoning', 2),
       (10, 'Offering', 2),
       (11, 'Following', 2),
       (12, 'Insurgent', 2);

insert into models(id, name, manufacturer_id)
VALUES (13, 'Fourstroke', 3);

insert into models(id, name, manufacturer_id)
VALUES (14, 'Hoplit Pi Plus', 4),
       (15, 'Aristos R', 4);

insert into models(id, name, manufacturer_id)
VALUES (16, 'Karate Monkey', 5);


-- bikes
insert into bikes(id, category, description, frame_material, frame_size, price, wheel_size, model_id)
VALUES (1, 'MTB',
        'The Stanton Sherpa is a 29er that retains the grin-inducing playfulness of a smaller-wheeled machine thanks to our uncompromising commitment to Positive Engineering. When new standards emerge we look for innovative engineering solutions that stay true to our principles, we don’t compromise our geometry just to make things fit.',
        'STEEL', 'M', 2500, '29', 4),
       (2, 'MTB',
        'The Sherpa Ti is our bench mark trail/XC-specific frame, designed to be run with 29” or 27.5″+ wheels.',
        'TITANIUM', 'M', 4500, '29', 8),
       (3, 'MTB',
        'The Slackline is our all day trail slayer – the rider’s ride. The choice of customers who’ve been there, ridden that, and know what they want.',
        'STEEL', 'XL', 2000, '27.5', 3),
       (4, 'MTB',
        'The choice of customers who’ve been there, ridden that, and know what they want.',
        'TITANIUM', 'M', 4000, '27.5', 7),
       (5, 'MTB',
        'Yeah the geometry’s progressive (isn’t everyone’s these days?) but it’s your riding that’s going to see the real progression. Whatever the Switch9er comes up against it won’t take no for answer, it’s just the way it’s built.',
        'STEEL', 'S', 2100, '29', 2),
       (6, 'MTB',
        'It all comes together in the Switch9er Ti – the ultimate enduro hardtail. The daddy of our playful Switch hardtail range, it’s a bike that does things it’s not supposed to, so you can do things you didn’t know you could.',
        'TITANIUM', 'M', 4800, '29', 6),
       (7, 'MTB',
        'We were born to play, and so was the Switchback Gen 3 – the most playful hardtail on the market.',
        'STEEL', 'M', 2500, '27.5', 1),
       (8, 'MTB',
        'The original aim of the Switchback was to design the most playful natured and confidence inspiring aggressive frame that we possibly could. The Switchback Ti is an evolution of this ethos.',
        'TITANIUM', 'M', 4800, '27.5', 5),
       (9, 'MTB',
        'A proper pocketknife covers all the slicing tasks you imagine, and the unexpected ones in between. Our Offering platform aims to do the same. The bike is stiff and responsive, pairing the smooth pedaling platform of our Following with some added padding so you can release the brakes and let gravity take over.',
        'CARBON', 'M', 9500, '29', 10),
       (10, 'MTB',
        'The Insurgent is the most shape-shifty Evil to date, and no matter the configuration, it’s built for the long send. Sure, you can comfortably pedal it uphill, but gravity is the bullseye that this machine nails every time. With all the best bits of our Wreckoning platform, and a little something extra, the Insurgent will mop up your mistakes and crack open possibilities.',
        'CARBON', 'XXL', 8000, '29', 12),
       (11, 'MTB',
        'This bike kicked off the shred-trail 29er movement, and we haven’t hit the mute button on loud, fast, and fun times. We need to let the volume roar and this bike needs to claw after every extra-credit popportunity. Oh, and it drifts uphill too.',
        'CARBON', 'L', 9300, '29', 11),
       (12, 'MTB',
        'The Wreckoning LS has nudged the limits of balance and grip, vetted by racers and weekend rippers alike. This is the bike you''ll find "too fast for me" with. The platform for lapping extra gnarly descents that offers a cozy pedaling position on the next climb.',
        'CARBON', 'M', 11000, '29', 9),
       (13, 'MTB',
        'Fourstroke 01 is an XCO performance bike that exceeds the demands of the world’s top racers.',
        'CARBON', 'S', 13000, '29', 13),
       (14, 'TOURING',
        'The Falkenjagd Hoplit Pi Plus is an uncompromising touring bike and copes well on all surfaces of this world.',
        'TITANIUM', 'M', 9000, '28', 14),
       (15, 'ROAD',
        'The Falkenjagd Aristos R is winner of the Design and Innovation Award 2022 and far ahead of its time! It convinces with versatile features and combines lightweight, robustness, comfort and agility with a high-quality equipment.',
        'TITANIUM', 'L', 7000, '28', 15),
       (16, 'MTB',
        'Karate Monkey delivers a resilient, lively ride on all sorts of gnarly terrain.',
        'STEEL', 'S', 2000, '27.5', 16);

-- pictures
insert into pictures(id, url)
VALUES (1, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477482/BikeShop/Bikes/gucc3xzdky8oxaow9nv8.jpg'),
       (2, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477633/BikeShop/Bikes/syjv9vq3guvevhbu05ot.jpg'),
       (3, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477663/BikeShop/Bikes/zhrtojxjufufptvofd2v.jpg'),
       (4, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477714/BikeShop/Bikes/avtkkclcmekwztvval0m.jpg'),
       (5, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477742/BikeShop/Bikes/jcpttwbzmhnm35nnffwx.jpg'),
       (6, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477778/BikeShop/Bikes/tlwahm8soh6o8fruosiv.jpg'),
       (7, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477804/BikeShop/Bikes/kgxlhu4hewvollooqkc9.jpg'),
       (8, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699477827/BikeShop/Bikes/ndgt2dwbi2sibknftsds.jpg'),
       (9, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525462/BikeShop/Bikes/r55akcose6apkpet3v6y.jpg'),
       (10, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525502/BikeShop/Bikes/vcl8a8pquosibwrqixym.jpg'),
       (11, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525548/BikeShop/Bikes/mbfys5ki3z2fwccyd7ja.jpg'),
       (12, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525691/BikeShop/Bikes/umpqmvrokltujgyv2gqb.jpg'),
       (13, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525746/BikeShop/Bikes/jfy8703vzgs3sa7qczwm.jpg'),
       (14, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525774/BikeShop/Bikes/t6pjlkdylysj0cuerov1.jpg'),
       (15, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699525989/BikeShop/Bikes/u0hmk1icr5ewloexsapq.jpg'),
       (16, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526019/BikeShop/Bikes/k8oyxmcdfp68iabzmw6o.jpg'),
       (17, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526050/BikeShop/Bikes/bt56kz4wifgqs6hwlro5.jpg'),
       (18, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526196/BikeShop/Bikes/f7f95xb9cq2en4vlblyv.jpg'),
       (19, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526341/BikeShop/Bikes/n6be6hkjoavbgylxlp6i.jpg'),
       (20, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526361/BikeShop/Bikes/kas5ohtdycphaqewg2on.jpg'),
       (21, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526852/BikeShop/Bikes/rsvucccqcju4pnrqhnv5.jpg'),
       (22, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699526903/BikeShop/Bikes/s8yirsixgq6ebtrotkku.jpg'),
       (23, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699527635/BikeShop/Bikes/fmqi4pqsnvdzvrwetc3j.jpg'),
       (24, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699527877/BikeShop/Bikes/wn4rnawkhhut75tpe100.jpg'),
       (25, 'https://res.cloudinary.com/duomcwff3/image/upload/v1699528054/BikeShop/Bikes/pakfmb37ryorfzrwqfen.jpg');

insert into bikes_images_url(bike_entity_id, images_url_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (9, 10),
       (9, 11),
       (10, 12),
       (10, 13),
       (10, 14),
       (11, 15),
       (11, 16),
       (11, 17),
       (12, 18),
       (12, 19),
       (12, 20),
       (13, 21),
       (13, 22),
       (14, 23),
       (15, 24),
       (16, 25);
