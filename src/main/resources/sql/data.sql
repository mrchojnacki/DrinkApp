use drinkApp;
insert ignore into Users (id, createdOn, email, lastUpdatedOn, password, userName)
values
    (1, NOW(6), '123@123.com', NOW(6), '123', 'admin');

insert into Ratings (valueOfRating, drink_id, user_id)
VALUES
    (1, 1, 1), (2, 2, 1), (3, 3, 1), (4, 4, 1), (4, 5, 1),
    (3, 6, 1), (2, 7, 1), (5, 8, 1), (2, 9, 1), (1, 10, 1),
    (4, 11, 1), (3, 12, 1), (4, 13, 1), (2, 14, 1), (5, 15, 0),
    (2, 16, 1), (3, 17, 1), (4, 18, 1), (2, 19, 1), (3, 20, 1),
    (4, 21, 1);

INSERT INTO drinks (name, method, createdOn, lastUpdatedOn, user_id)
VALUES
    ('Bellini', 'Pour peach puree into the mixing glass with ice, add the Prosecco wine. Stir gently and pour in a chilled flute glass.  Note: PUCCINI - Fresh Mandarin Orange Juice; ROSSINI - Fresh Strawberry Puree; TINTORETTO - Fresh Pomegranate Juice.', NOW(6), NOW(6), 1),
    ('Black Russian', 'Pour the ingredients into the old fashioned glass filled with ice cubes. Stir gently.  Note: WHITE RUSSIAN - Float fresh cream on the top and stir in slowly.', NOW(6), NOW(6), 1),
    ('Bloody Mary', 'Stir gently all the ingredients in a mixing glass with ice, pour into rocks glass. Note: If requested served with ice, pour into highball glass.', NOW(6), NOW(6), 1),
    ('Caipirinha', 'Place lime and sugar into a double old fashioned glass and muddle gently. Fill the glass with cracked ice and add Cachaça. Stir gently to involve ingredients. Note: CAIPIROSKA - Instead of Cachaça use Vodka;', NOW(6), NOW(6), 1),
    ('Champagne Cocktail', 'Place the sugar cube with 2 dashes of bitters in a large Champagne glass, add the cognac. Pour gently chilled Champagne.', NOW(6), NOW(6), 1),
    ('Corpse Reviver #2', 'Pour all ingredients into shaker with ice. Shake well and strain in chilled cocktail glass.', NOW(6), NOW(6), 1),
    ('Cosmopolitan', 'Add all ingredients into a cocktail shaker filled with ice. Shake well and strain into a large cocktail glass.', NOW(6), NOW(6), 1),
    ('Cuba Libre', 'Build all ingredients in a highball glass filled with ice.', NOW(6), NOW(6), 1),
    ('French 75', 'Pour all the ingredients, except Champagne, into a shaker. Shake well and strain into a Champagne flute. Top up with Champagne. Stir gently.', NOW(6), NOW(6), 1),
    ('French Connection', 'Pour all ingredients directly into an old fashioned glass filled with ice cubes.', NOW(6), NOW(6), 1),
    ('Golden Dream', 'Pour all ingredients into shaker filled with ice. Shake briskly for few seconds. Strain into chilled cocktail glass.', NOW(6), NOW(6), 1),
    ('Grasshopper', 'Pour all ingredients into shaker filled with ice. Shake briskly for few seconds. Strain into chilled cocktail glass.', NOW(6), NOW(6), 1),
    ('Hemingway Special', 'Pour all ingredients into a shaker with ice. Shake well and strain into a large cocktail glass.', NOW(6), NOW(6), 1),
    ('Horse''s Neck', 'Pour Cognac and ginger ale directly into a highball glass with ice cubes. Stir gently. If preferred, add dashes of Angostura Bitter.', NOW(6), NOW(6), 1),
    ('Irish Coffee', 'Warm black coffee is poured into a pre-heated Irish coffee glass. Whiskey and at least one teaspoon of sugar is added and stirred until dissolved. Fresh thick chilled cream is carefully poured over the back of a spoon held just above the surface of the coffee. The layer of cream will float on the coffee without mixing. Plain sugar can be replaced with sugar syrup', NOW(6), NOW(6), 1),
    ('KIR', 'Pour Crème de Cassis into glass, top up with white wine. Note: KIR ROYAL - Use Champagne instead of white wine', NOW(6), NOW(6), 1),
    ('Long Island Ice Tea', 'Add all ingredients into a highball glass filled with ice.', NOW(6), NOW(6), 1),
    ('Mai-Tai', 'Add all ingredients into a shaker with ice. Shake and pour into a double rocks glass or a highball glass.  The Martinique molasses rum used by Trader Vic was not an Agricole rum but a type of “rummy” from molasses.', NOW(6), NOW(6), 1),
    ('Margarita', 'Add all ingredients into a shaker with ice. Shake and strain into a chilled cocktail glass', NOW(6), NOW(6), 1),
    ('Mimosa', 'Pour orange juice into a flute glass and gently pour the sparkling wine. Stir gently. Note: Also known as Buck''s Fizz.', NOW(6), NOW(6), 1),
    ('Mint Julep', 'In Julep Stainless Steel Cup gently muddle the mint with sugar and water. Fill the glass with cracked ice, add the Bourbon and stir well until the cup frosts.', NOW(6), NOW(6), 1)
;

insert into alcohol_ingredients (alcoholType, volumeMillilitres)
values
    ('Prosecco', 100), ('Vodka', 50), ('Coffe Liqueur', 20),
    ('Vodka', 45), ('Cachaca', 60), ('Chilled Champagne', 90),
    ('Cognac', 10), ('Agnostura bitters', 1), ('Grand Marnier', 2),
    ('Gin', 30), ('Cointreau', 30), ('Lillet Blanc', 30),
    ('Absinthe', 1), ('Vodka Citron', 40), ('Cointreau', 15),
    ('White Rum', 50), ('Champagne', 60), ('Cognac', 35),
    ('Amaretto', 35), ('Galliano', 20), ('Triple Sec', 20),
    ('Creme de Cacao', 20), ('Creme de Menthe', 20), ('Rum', 60),
    ('Maraschino Luxardo', 15), ('Cognac', 40), ('Ginger Ale', 120),
    ('Irish Whiskey', 50), ('Dry White Wine', 90), ('Creme de Cassis', 10),
    ('Vodka', 15), ('Tequila', 15), ('White Rum', 15),
    ('Gin', 15), ('Amber Jamaican Rum', 30), ('Martinique Molasses Rhum', 30),
    ('Orange Curacao', 15), ('Tequila Agave', 50), ('Prosecco', 75),
    ('Bourbon Whiskey', 60)
;

insert into fill_ingredients (fill, amount)
values
    ('White Peach Puree', '50 ml'), ('Tomato Juice', '90 ml'), ('Fresh Lemon Juice', '15 ml'),
    ('Worcestershire Sauce', '2 dashes'), ('Tabasco', 'Spoonful'), ('Celery Salt', 'Half Spoon'),
    ('Pepper', 'Pinch'), ('Lime Cut Into Small Wedges', '1'), ('Teaspoon White Cane Sugar', '4'),
    ('Sugar Cube', '1'), ('Fresh Lemon Juice', '30 ml'), ('Cranberry Juice', '30 ml'),
    ('Coca Cola', '120 ml'), ('Fresh Lemon Juice', '10 ml'), ('Sugar Syrup', '15 ml'),
    ('Fresh Orange Juice', '20 ml'), ('Fresh Cream', '10 ml'), ('Fresh Cream', '20 ml'),
    ('Grapefruit Juice', '40 ml'), ('Fresh Lime Juice', '15 ml'), ('Hot Coffee', '120 ml'),
    ('Chilled Fresh Cream', '50 ml'), ('Sugar', '1 Teaspoon'), ('Fresh Lemon Juice', '30 ml'),
    ('Simple Syrup', '20 ml'), ('Orgeat Syrup', '15 ml'), ('Simple Syrup', '7,5 ml'),
    ('Fresh Lime Juice', '30 ml'), ('Fresh Orange Juice', '75 ml'), ('Fresh Mint Srpigs', '4'),
    ('Powdered Sugar', '1 Tablespoon'), ('Water', '2 Tablespoons')
;

insert into drinks_alcohol_ingredients (drink_id, alcohol_ingredient_id)
values
    (1, 1), (2, 2), (2, 3), (3, 4), (4, 5), (5, 6), (5, 7),
    (5, 8), (5, 9), (6, 10), (6, 11), (6, 12), (6, 13), (7, 14),
    (7, 15), (8, 16), (9, 10), (9, 17), (10, 18), (10, 19),
    (11, 20), (11, 21), (12, 22), (12, 23), (13, 24), (13, 25),
    (14, 26), (14, 27), (14, 8), (15, 28), (16, 29), (16, 30),
    (17, 31), (17, 32), (17, 33), (17, 34), (17, 15), (18, 35),
    (18, 36), (18, 37), (19, 21), (19, 38), (20, 39), (21, 40)
;

insert into drinks_fill_ingredients (drink_id, fill_ingredient_id)
values
    (1, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7),
    (4, 8), (4, 9), (5, 10), (6, 11), (7, 3), (7, 12), (8, 13),
    (8, 14), (9, 3), (9, 15), (11, 16), (11, 17), (12, 18),
    (13, 19), (13, 20), (15, 21), (15, 22), (15, 23), (17, 24),
    (17, 25), (17, 13), (18, 26), (18, 27), (18, 28), (19, 28),
    (20, 29), (21, 30), (21, 31), (21, 32)
;