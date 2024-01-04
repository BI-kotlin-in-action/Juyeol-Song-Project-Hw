CREATE TABLE IF NOT EXISTS lotto (
    lotto_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    lotto_numbers VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance BIGINT NOT NULL,
    role VARCHAR(255) NOT NULL,
    version BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS lotto_record (
    user_id BIGINT NOT NULL,
    lotto_id BIGINT NOT NULL,
    round INT NOT NULL,
    current_counter_of_this_round BIGINT NOT NULL,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (user_id, lotto_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS lotto_round_control (
    lotto_round_control_id BIGINT PRIMARY KEY,
    round INT NOT NULL
);

CREATE TABLE IF NOT EXISTS queue_working_counter (
    queue_round INT PRIMARY KEY,
    counter BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS result_record (
    result_record_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    round INT NOT NULL,
    current_counter_of_this_round BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    username VARCHAR(255) NOT NULL,
    matched_number VARCHAR(255) NOT NULL,
    num_of_matched INT NOT NULL,
    prize_rank VARCHAR(255) NOT NULL,
    prize_amount BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS winning_record (
    winning_record_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    round INT NOT NULL,
    lotto_numbers VARCHAR(255) NOT NULL
);
