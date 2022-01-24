/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StoreDetailComponent from '@/entities/store/store-details.vue';
import StoreClass from '@/entities/store/store-details.component';
import StoreService from '@/entities/store/store.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Store Management Detail Component', () => {
    let wrapper: Wrapper<StoreClass>;
    let comp: StoreClass;
    let storeServiceStub: SinonStubbedInstance<StoreService>;

    beforeEach(() => {
      storeServiceStub = sinon.createStubInstance<StoreService>(StoreService);

      wrapper = shallowMount<StoreClass>(StoreDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { storeService: () => storeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStore = { id: 123 };
        storeServiceStub.find.resolves(foundStore);

        // WHEN
        comp.retrieveStore(123);
        await comp.$nextTick();

        // THEN
        expect(comp.store).toBe(foundStore);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStore = { id: 123 };
        storeServiceStub.find.resolves(foundStore);

        // WHEN
        comp.beforeRouteEnter({ params: { storeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.store).toBe(foundStore);
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
