INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('c2c15f60-2f0d-11ec-8d3d-0242ac130003', 'dummy', 'dummy');

INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('a3d23d8c-2f11-11ec-8d3d-0242ac130003','dummy_friend', 'dummy_friend');
INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('c5489bdc-2f11-11ec-8d3d-0242ac130003','dummy_friend_1', 'dummy_friend_1');
INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('cff87ff2-2f11-11ec-8d3d-0242ac130003','dummy_friend_2', 'dummy_friend_2');
INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('d995bb10-2f11-11ec-8d3d-0242ac130003','dummy_friend_3', 'dummy_friend_3');

INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('e76e1f52-2f11-11ec-8d3d-0242ac130003','dummy_not_friend', 'dummy_not_friend');
INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('efece06e-2f11-11ec-8d3d-0242ac130003','dummy_not_friend_1', 'dummy_not_friend_1');
INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('f7a77148-2f11-11ec-8d3d-0242ac130003','dummy_not_friend_2', 'dummy_not_friend_2');
INSERT INTO stranding_user(stranding_user_id, stranding_user_name, stranding_user_login) values('ff395f7a-2f11-11ec-8d3d-0242ac130003','dummy_not_friend_3', 'dummy_not_friend_3');

INSERT INTO stranding_user_x_friends( stranding_user_id, friend_id )
	VALUES(  
		'c2c15f60-2f0d-11ec-8d3d-0242ac130003'
		,'a3d23d8c-2f11-11ec-8d3d-0242ac130003' -- dummy_friend
	);

INSERT INTO stranding_user_x_friends( stranding_user_id, friend_id )
	VALUES(  
		'c2c15f60-2f0d-11ec-8d3d-0242ac130003'
		,'c5489bdc-2f11-11ec-8d3d-0242ac130003' -- dummy_friend_1
	);

INSERT INTO stranding_user_x_friends( stranding_user_id, friend_id )
	VALUES(  
		'c2c15f60-2f0d-11ec-8d3d-0242ac130003'
		,'cff87ff2-2f11-11ec-8d3d-0242ac130003' -- dummy_friend_2
	);

INSERT INTO stranding_user_x_friends( stranding_user_id, friend_id )
	VALUES(  
		'c2c15f60-2f0d-11ec-8d3d-0242ac130003'
		,'d995bb10-2f11-11ec-8d3d-0242ac130003' -- dummy_friend_3
	);

-- FRIEND'S POSTS

-- POSTS FRIEND 1
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'174d6195-000b-465c-bcda-1ee5ee2da36f'
				,'A equipe de suporte precisa saber que o módulo de recursão paralela complexificou o merge da execução parelela de funções em multi-threads.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend' )
				,'2021-06-01 12:55:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'dc4cdb72-e9b2-4327-923d-c511679cae74'
				,'Desde ontem a noite a normalização da data causou o bug do JSON compilado a partir de proto-buffers.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend' )
				,'2021-06-01 12:50:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'39c1062a-3790-4fcc-b394-9c199dd32e6f'
				,'Com este commit, o último pull request desse SCRUM otimizou a renderização de uma compilação com tempo acima da media.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend' )
				,'2021-05-30 12:45:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'd0de1083-c1ef-4536-8b86-0592f20d3bf9'
				,'Com este commit, a disposição dos elementos HTML facilitou a resolução de conflito da execução parelela de funções em multi-threads.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend' )
				,'2021-05-30 12:40:00.000'
			);
			
-- POSTS FRIEND 2
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'7d9c79a6-d785-4e4a-912e-2b05cbaf46bc'
				,'Dado o fluxo de dados atual, a normalização da data corrigiu o bug dos parametros passados em funções privadas.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_1' )
				,'2021-06-01 12:56:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'23d39885-9565-464f-9e3a-c405a0f9fe59'
				,'Nesse pull request, a otimização de performance da renderização do DOM otimizou a renderização de estados estáticos nos componentes da UI.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_1' )
				,'2021-06-01 12:51:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'8863ca30-8d77-401a-8ad7-42e66ec3b88a'
				,'Explica pro Product Onwer que o deploy automatizado no Heroku facilitou a resolução de conflito na organização alfanumérico dos arrays multidimensionais'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_1' )
				,'2021-05-30 12:46:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'da77d854-b63f-48af-9aa1-becda5b574f6'
				,'Com este commit, o gerenciador de dependências do frontend complexificou o merge de uma configuração Webpack eficiente nos builds.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_1' )
				,'2021-05-30 12:41:00.000'
			);
			
-- POSTS FRIEND 3
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'125adbf0-0bf2-4ea6-8c76-d3b9fcab894f'
				,'Explica pro Product Onwer que a disposição dos elementos HTML causou a race condition na criação de novos polyfills para suportar os processos.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_2' )
				,'2021-06-01 12:57:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'2ed3c43b-842c-41a9-aa2e-51dc05c2fe07'
				,'Fala pro cliente que o deploy automatizado no Heroku otimizou a renderização da renderização de floats parciais.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_2' )
				,'2021-06-01 12:52:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'663f44f3-c0f7-48f6-ab00-82f32eab6738'
				,'A equipe de suporte precisa saber que a otimização de performance da renderização do DOM superou o desempenho da execução parelela de funções em multi-threads.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_2' )
				,'2021-05-30 12:47:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'32d635e7-e71d-4c6a-b6a1-b2ed3d78102d'
				,'Nesse pull request, um erro não identificado facilitou a resolução de conflito do JSON compilado a partir de proto-buffers.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_2' )
				,'2021-05-30 12:42:00.000'
			);
			
-- POSTS FRIEND 4
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'03429b0d-c371-4e3f-bc54-4498f56c8f51'
				,'A equipe de suporte precisa saber que a otimização de performance da renderização do DOM causou o bug no fechamento automático das tags.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_3' )
				,'2021-06-01 12:58:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'ac8149ae-84d2-4ced-82b5-acdfca3497b4'
				,'Fala pro cliente que a compilação final do programa corrigiu o bug de compilação multi-plataforma de forma asincrona.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_3' )
				,'2021-06-01 12:53:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'f6aaa950-3643-4054-a160-1d70cf87094a'
				,'A equipe de suporte precisa saber que um erro não identificado facilitou a resolução de conflito na criação de novos polyfills para suportar os processos.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_3' )
				,'2021-05-30 12:48:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'8d7ffd80-77b7-47df-9056-c5d3b169ac34'
				,'A equipe de suporte precisa saber que um erro não identificado causou a race condition na interpolação dinâmica de strings.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_friend_3' )
				,'2021-05-30 12:43:00.000'
			);
			
-- NO FRIEND'S POSTS

-- NO FRIEND'S POSTS 1
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'7bb4dfac-94e7-4ce4-bc5e-c384da4ca52f'
				,'Com este commit, a otimização de performance da renderização do DOM facilitou a resolução de conflito no parse retroativo do DOM.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend' )
				,'2021-06-01 12:59:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'e633ce16-2e4e-4296-b13d-33bd6c1f9969'
				,'A pulsão é o elo entre o somático e o psíquico onde o recalque da enunciação denega a natureza do sofrimento que define o modo de relação do sujeito com o seu sofrimento.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend' )
				,'2021-06-01 12:54:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'582a5503-24af-4d89-82de-11ecf5e0eb35'
				,'Freud, em correspondência a Jung, cita que o movimento da pulsão escópica contribui para a colocação em cena cujos efeitos produzem o recalque desta mesma etilogia sexual da neurose.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend' )
				,'2021-05-30 12:49:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'383afdb8-e9a3-476e-b283-3f41720c5b41'
				,'A pulsão é o elo entre o somático e o psíquico onde o recalque da enunciação constrói um delírio que faz com que o sujeito se constitua no campo do Outro.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend' )
				,'2021-05-30 12:44:00.000'
			);
			
-- NO FRIEND'S POSTS 2
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'3517c84d-91b4-4e29-8a79-94878727b481'
				,'O sonho é a realização de um desejo que os processos oníricos — e, por extensão, todo o inconsciente — se acompanha então de inconvenientes não negligenciáveis que faz com que o sujeito se constitua no campo do Outro.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_1' )
				,'2021-06-01 12:54:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'baad01f0-d11b-4fb7-9902-a9fefd06608d'
				,'A psicanálise parte da idéia de que o estado amoroso na hipnose realiza a travessia da fantasia sem que isso impeça a extração de gozo.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_1' )
				,'2021-06-01 12:49:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'43560116-a800-4360-8e53-19c4579f7904'
				,'Explica pro Product Onwer que o deploy automatizado no Heroku facilitou a resolução de conflito na organização alfanumérico dos arrays multidimensionais'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_1' )
				,'2021-05-30 12:44:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'4a391001-6afd-40f3-8b93-0fe17edfb565'
				,'Ora, o recalque da enunciação realiza a travessia da fantasia que não há relação sexual.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_1' )
				,'2021-05-30 12:39:00.000'
			);
			
-- NO FRIEND'S POSTS 3
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'4e88efb8-f9c3-4811-9459-c2699b9655fb'
				,'As experiências acumuladas demonstram que a necessidade de renovação processual obstaculiza a apreciação da importância dos relacionamentos verticais entre as hierarquias.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_2' )
				,'2021-06-01 12:53:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'bfeadc97-ad2a-43e5-8e9f-df5f1f1666d8'
				,'No mundo atual, a hegemonia do ambiente político possibilita uma melhor visão global do sistema de formação de quadros que corresponde às necessidades.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_2' )
				,'2021-06-01 12:48:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'9a18428d-ff24-4da1-875e-b899d7c58cc5'
				,'No mundo atual, a mobilidade dos capitais internacionais causa impacto indireto na reavaliação do orçamento setorial.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_2' )
				,'2021-05-30 12:43:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'b4effec0-5685-418a-a783-ef7062f02640'
				,'Acima de tudo, é fundamental ressaltar que a contínua expansão de nossa atividade maximiza as possibilidades por conta das formas de ação.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_2' )
				,'2021-05-30 12:38:00.000'
			);
			
-- NO FRIEND'S POSTS 4
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'a15b66a6-6484-4b01-93d4-e0cb366a83da'
				,'No entanto, não podemos esquecer que a adoção de políticas descentralizadoras agrega valor ao estabelecimento do levantamento das variáveis envolvidas.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_3' )
				,'2021-06-01 12:52:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'17b29ac5-79dd-4168-b275-a408252c5909'
				,'Podemos já vislumbrar o modo pelo qual a percepção das dificuldades estende o alcance e a importância das regras de conduta normativas.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_3' )
				,'2021-06-01 12:47:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'13e09e19-4dff-4ed2-b40a-fb047a078c27'
				,'Do mesmo modo, o entendimento das metas propostas aponta para a melhoria do impacto na agilidade decisória.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_3' )
				,'2021-05-30 12:42:00.000'
			);
			
INSERT INTO post(post_id, post_content, stranding_user_id, creation_date)
			VALUES(
				'0803b8fe-42dd-441c-85c3-6fb9073a96b4'
				,'Percebemos, cada vez mais, que o julgamento imparcial das eventualidades afeta positivamente a correta previsão das diretrizes de desenvolvimento para o futuro.'
				,( SELECT stranding_user_id FROM stranding_user WHERE stranding_user_name = 'dummy_not_friend_3' )
				,'2021-05-30 12:37:00.000'
			);