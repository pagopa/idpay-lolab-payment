import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { KVApi } from './apis/lolab-kv-api.js';

export const options = {
  scenarios: {
    constant_request_rate: {
      executor: 'constant-arrival-rate',
      rate: 1000,
      timeUnit: '1s', // 1000 iterations per second, i.e. 1000 RPS
      duration: '20s',
      preAllocatedVUs: 50, // how large the initial pool of VUs would be
      maxVUs: 120, // if the preAllocatedVUs are not enough, we can initialize more
    }
  },
  thresholds: {
    'http_req_duration': ['p(99)<1500'], // 99% of requests must complete below 1.5s
    'logged in successfully': ['p(99)<1500'], // 99% of requests must complete below 1.5s
  },
};

const lolabKv = new KVApi("http://localhost/lolab/kv");

const generator = {
    currentKey: 0,
    next(prefix) {
        return prefix + this.currentKey++;
    }
}

export default () => {
    const key = generator.next("key_");
    const value = generator.next("value_");
    check(
        lolabKv.put(key, value),
        {
            'Is OK': (r) => r.status === 200 || r.status === 201,
        }
    );
    check(lolabKv.get(key), {
        'Is same put': (r) => r.body.toString() === value,
    });
};
