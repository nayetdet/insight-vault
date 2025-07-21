insert into users (created_at, updated_at, email, username, password, role)
values (now(), now(), 'string@gmail.com', 'string', '$argon2id$v=19$m=16384,t=2,p=1$dH/+niutcG7iruXOTltxOQ$nFgOfDY+RNaut8njlxgUmAXTX5zlZ/FajjD49hME/iU', 'USER')
on conflict (username) do nothing;
