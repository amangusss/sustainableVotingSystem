insert into candidates (full_name, party, district, photo_url, photo_version, vote_count)
values
  ('Aibek Usubaliev', 'Independent', 'Bishkek', 'https://via.placeholder.com/80', 1, 0),
  ('Aizada Sadykova', 'Progress Party', 'Osh', 'https://via.placeholder.com/80', 1, 0),
  ('Nurlan Toktogulov', 'Unity', 'Bishkek', 'https://via.placeholder.com/80', 1, 0);

insert into voters (full_name, passport_id, district, password_hash, is_voted, created_at)
values ('Admin', 'admin', 'Bishkek', '{noop}admin', false, now());
