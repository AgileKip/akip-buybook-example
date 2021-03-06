/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublisherDetailComponent from '@/entities/publisher/publisher-details.vue';
import PublisherClass from '@/entities/publisher/publisher-details.component';
import PublisherService from '@/entities/publisher/publisher.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Publisher Management Detail Component', () => {
    let wrapper: Wrapper<PublisherClass>;
    let comp: PublisherClass;
    let publisherServiceStub: SinonStubbedInstance<PublisherService>;

    beforeEach(() => {
      publisherServiceStub = sinon.createStubInstance<PublisherService>(PublisherService);

      wrapper = shallowMount<PublisherClass>(PublisherDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { publisherService: () => publisherServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublisher = { id: 123 };
        publisherServiceStub.find.resolves(foundPublisher);

        // WHEN
        comp.retrievePublisher(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publisher).toBe(foundPublisher);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublisher = { id: 123 };
        publisherServiceStub.find.resolves(foundPublisher);

        // WHEN
        comp.beforeRouteEnter({ params: { publisherId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publisher).toBe(foundPublisher);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
